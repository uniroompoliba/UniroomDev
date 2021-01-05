package com.example.uniroomf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class menuPrincDoc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_princ_doc)
    }

    override fun onResume() {
        super.onResume() // Costruttore della superclasse

        //Prendo i dati degli utenti passati tramite bundle
        val bundle : Bundle? = intent.extras
        var email = bundle!!.get("user").toString()
        var pw = bundle!!.get("pw").toString()
        var ruolo = bundle!!.get("ruolo").toString()

        var testo = findViewById<TextView>(R.id.nomeUtente)

        // Estraggo il nome
        Thread{
            var myRQ = Volley.newRequestQueue(this)
            var urlEstraz = "http://uniroompoliba.altervista.org/public/utilityScripts/estraiNome.php"

            var oggettoDati = JSONObject()

            oggettoDati.put("user",email)
            oggettoDati.put("pw",pw)
            oggettoDati.put("ruolo",ruolo)

            var recNome = JsonObjectRequest(Request.Method.POST, urlEstraz, oggettoDati,
                    Response.Listener { response ->

                        testo.setText("Benvenuto prof." + response.get("Nome").toString())

                        // Fine request
                    },
                    Response.ErrorListener { error ->
                        println("Errore ricevuto nell'inserire il nome: " +  error.toString())

                    })

            myRQ.add(recNome)
        }.start()


        //Aggiungo il listener al bottone della nuova prenotazione
        var newPrenBtn = findViewById<Button>(R.id.newPrenDoc)
        newPrenBtn.setOnClickListener {
            // Creo intent per il passaggio
            var intent = Intent(applicationContext,prenotazDocActivity::class.java)
            intent.putExtra("user",email)
            intent.putExtra("pw",pw)
            startActivity(intent)
        }

        // Aggiungo il listener al bottone dello storico
        var histBtn = findViewById<Button>(R.id.storPrenDoc)
        histBtn.setOnClickListener {
            //Creo intent per il passaggio
            var histIntent = Intent(applicationContext,storicoDocActivity::class.java)
            histIntent.putExtra("user",email)
            histIntent.putExtra("password",pw)
            startActivity(histIntent)

        }

        var setBtn = findViewById<Button>(R.id.settings)
        setBtn.setOnClickListener {
            // Aggiungo il listener al bottone delle impostazioni
            var intent3 = Intent(this,acivityImpostazioni::class.java)
            intent3.putExtra("ruolo","Docente")
            intent3.putExtra("user",email)
            intent3.putExtra("pw",pw)
            startActivity(intent3)
        }

        // Imposto il bottone del logout e ne setto il listener
        var logOutBtn = findViewById<ImageButton>(R.id.logout)
        logOutBtn.setOnClickListener {

            // Passo semplicemente alla schermata iniziale
            var tornaHome = Intent(this, MainActivity::class.java)

            Toast.makeText(this, "Arrivederci", Toast.LENGTH_LONG ).show()
            startActivity(tornaHome)
        }

    }
}