package com.example.uniroomf

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.*
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class acivityImpostazioni : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

    }



    override fun onResume() {
        super.onResume()

        // Prendo i dati dal bundle
        val bundleImp = intent.extras
        var ruolo = bundleImp!!.getString("ruolo")
        var user = bundleImp!!.getString("user")
        var pw = bundleImp!!.getString("pw")

        // Request per caricare i dati basata sui dati passati dal login
        var oggettiImp = JSONObject()
        oggettiImp.put("email", user)
        oggettiImp.put("password", pw)
        oggettiImp.put("ruolo", ruolo)

        var myRQ = Volley.newRequestQueue(this)
        var urlRecuperaDati = "http://uniroompoliba.altervista.org/public/utilityScripts/recuperaDatiImpostazioni.php"

        // Metodo necessario alla comunicazione
        //Aggiungiamo gli header per accettare la comunicazione json
        fun setMyHeader()
        {
            var headers = HashMap<String, String>()
            headers.put("Content-Type", "application/json")
            headers.put("Accept", "application/json")
        }

        setMyHeader()

        Thread{
            // I dati sono passati correttamente tra una activity e l'altra
            var prendiDati = JsonObjectRequest(Request.Method.POST, urlRecuperaDati, oggettiImp,
                    Response.Listener { response ->

                        //Dal JsonObjectRicevuto imposto i dati della settings
                        var nomeEcognome =
                                response.get("Nome").toString() + " " + response.get("Cognome").toString()

                        // Setto il nome e cognome
                        findViewById<TextView>(R.id.nomeValue).setText(nomeEcognome)

                        // Setto l'email
                        findViewById<TextView>(R.id.emailValue).setText(response.get("Email").toString())

                        // Setto la matricola
                        findViewById<TextView>(R.id.matValue).setText(response.get("Matricola").toString())

                        // Setto il ruolo
                        findViewById<TextView>(R.id.roleValue).setText(ruolo)

                        // Setto il numero di prenotazioni
                        findViewById<TextView>(R.id.numValue).setText(response.get("numPren").toString())
                    },
                    Response.ErrorListener { error ->
                        println("Errore: " + error.toString())
                    })

            myRQ.add(prendiDati)
        }.start()


        // Aggiungo il listener del cambio password
        var changePwBtn = findViewById<Button>(R.id.modPassw)
        changePwBtn.setOnClickListener {

            var newPass = " "

            // Creo l'alert di inserimento
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Inserire nuova password")
            val input = EditText(this)

            input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)

            val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT)
            input.layoutParams = lp
            builder.setView(input)

            // Settiamo il bottone dell'inserimento
            builder.setPositiveButton("Inserisci") { dialog, which : Int ->

                newPass = input.text.toString()
                // Invio i dati della nuova password - passo i dati all'activity per il cambio passsword
                oggettiImp.put("nuovaPassword", newPass)

                // Parametri per la comunicazione
                var urlCambioPass = "http://uniroompoliba.altervista.org/public/utilityScripts/cambiaPassword.php"

                // Quando l'utente effettua la scelta --> Avviare request
                if(newPass != " ")
                {
                    Thread{
                        // Request
                        var cambioPassword = JsonObjectRequest(Request.Method.POST, urlCambioPass, oggettiImp,
                                Response.Listener { response ->
                                    // Toast di avvenuto cambiamento
                                    Toast.makeText(this, response.get("tipoErr").toString(), Toast.LENGTH_LONG).show()
                                    var passToMenu = Intent(this, menuPrincDoc::class.java)
                                    passToMenu.putExtra("user", user)
                                    passToMenu.putExtra("pw", pw)

                                    // Avvio la nuova activity
                                    startActivity(passToMenu)
                                },
                                Response.ErrorListener { error ->
                                    // Errore
                                    println(error.toString())
                                })

                        myRQ.add(cambioPassword)
                    }.start()
                }
                else
                {
                    Toast.makeText(this, "Campo vuoto. Reinserire.", Toast.LENGTH_LONG).show()
                }

            }
            builder.show()

        }

        // Aggiungo il listener per l'eliminazione dell'account
        var delBtn = findViewById<Button>(R.id.cancProfile)
        delBtn.setOnClickListener {

            var urlDelAccount = "http://uniroompoliba.altervista.org/public/utilityScripts/eliminaAccount.php"

            // Request per eliminare l'account
            Thread{

                // Nuova JSONObjectRequest
                var delAccount = JsonObjectRequest(Request.Method.POST, urlDelAccount, oggettiImp,
                        Response.Listener { response ->
                            //Toast di avvenuta eliminazione, quindi passo alla primissima schermata
                            if (response.get("tipoErr").toString().equals("Eliminazione correttamente avvenuta. Arrivederci!")) {
                                // Eliminazione correttamente avvenuta
                                Toast.makeText(this, response.get("tipoErr").toString(), Toast.LENGTH_LONG).show()
                                var tornaInizio = Intent(this, MainActivity::class.java)
                                startActivity(tornaInizio)
                            }
                        },
                        Response.ErrorListener { error ->
                            println(error.toString())
                        })

                myRQ.add(delAccount)
            }.start()

        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }
}