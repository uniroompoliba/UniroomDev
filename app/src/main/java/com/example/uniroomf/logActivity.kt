package com.example.uniroomf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonReader
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class logActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
//definire variabili di accesso
    override fun onResume() {
        super.onResume()

        var logBTM = findViewById<Button>(R.id.register)

        //listener
        logBTM.setOnClickListener {
            Thread{

                //Prendo gli elementi dalle EditText
                var username = findViewById<EditText>(R.id.username).text.toString()
                var password = findViewById<EditText>(R.id.password).text.toString()


                //definire eventi comunicazione
                var url = "http://uniroompoliba.altervista.org/public/accesso.php"
                var myRQ = Volley.newRequestQueue(this)

                fun setMyHeader()
                {
                    var headers = HashMap<String, String>()
                    headers.put("Content-Type", "application/json")
                    headers.put("Accept", "application/json")
                }

                setMyHeader()

                var accEmail = JSONObject()
                    accEmail.put("utente", username)
                    accEmail.put("password", password)


                //definire la richiesta di accesso al login attraverso la JsonObjectRequest

                var richiesta = JsonObjectRequest(Request.Method.POST, url, accEmail,
                        Response.Listener { response ->
                            var messaggio = response.get("tipoErr").toString()
                            var ruolo = response.get("ruolo").toString()

                            //Modifica Aldo 15/12/2020 - 16:04 - Sistemazione del messaggio di commit
                            if(messaggio != "Email o password non valide" && messaggio != "Email o password vuota")
                            {
                                // Login avvenuto correttamente
                                //Controllo il ruolo
                                    println(ruolo)
                                    if(ruolo.equals("Docente"))
                                    {
                                        //Aprire menu Docente
                                        Toast.makeText(this,  messaggio, Toast.LENGTH_SHORT).show()
                                        var passaggio = Intent(this, menuPrincDoc::class.java)

                                        //Aggiungo username e password, mi servirà nello script PHP

                                        // Refuso 2/01/2021 -> Aggiungo anche il ruolo, utile per recapActivity

                                        passaggio.putExtra("user",username)
                                        passaggio.putExtra("pw",password)
                                        passaggio.putExtra("ruolo",ruolo)

                                        startActivity(passaggio)  //passaggio da login andato a buon fine al menù prenotazione del docente
                                    }
                                else
                                    {
                                        // Aprire menu studente
                                        Toast.makeText(this, messaggio, Toast.LENGTH_SHORT).show()
                                        var passaggio2 = Intent(this,menuPrincStud::class.java)

                                        //Aggiungo username e password, mi servirà nello script PHP

                                        // Refuso 2/01/2021 -> Aggiungo anche il ruolo, utile per recapActivity

                                        passaggio2.putExtra("user",username)
                                        passaggio2.putExtra("pw",password)
                                        passaggio2.putExtra("ruolo",ruolo)

                                        startActivity(passaggio2) // Passaggio andato a buon fine - apro prenotazione studente

                                    }
                            }
                            else
                            {
                                // Credenziali non valide
                                Toast.makeText(this,  messaggio, Toast.LENGTH_LONG).show()
                            }

                        }, Response.ErrorListener { error ->
                            Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
                        }

                )
                myRQ.add(richiesta)

            }.start()
        }

    // Bottone per il recupero password
    var recPw = findViewById<Button>(R.id.button)

    recPw.setOnClickListener{

        // Creo l'alert di inserimento
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Inserire Email per il recupero")
        val input = EditText(this)

        val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT)
        input.layoutParams = lp
        builder.setView(input)

        // Imposto il pulsante di inserimento
        builder.setPositiveButton("Inserisci"){dialog, which : Int ->
            var emailRif = input.text.toString()

            var oggetto = JSONObject()
            oggetto.put("email", emailRif)

            // Parametri per la request
            var myRQ = Volley.newRequestQueue(this)
            var urlRecPw = "http://uniroompoliba.altervista.org/public/utilityScripts/recPass.php"

            Thread{

                var mandaPw = JsonObjectRequest(Request.Method.POST, urlRecPw, oggetto,
                Response.Listener { response ->
                      Toast.makeText(this,response.get("tipoErr").toString(), Toast.LENGTH_LONG).show()
                },
                Response.ErrorListener { error ->
                    println("Errore ricevuto nel recupero password: " + error.toString())
                })

                myRQ.add(mandaPw)
            }.start()
        }

        builder.show()
    }
    }
}