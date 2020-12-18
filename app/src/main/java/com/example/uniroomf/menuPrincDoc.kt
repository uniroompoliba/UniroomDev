package com.example.uniroomf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class menuPrincDoc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_princ_doc)
    }

    override fun onResume() {
        super.onResume() // Costruttore della superclasse

        //Prendo i dati degli utenti passati tramite bundle
        val bundle = intent.extras
        var email = bundle!!.get("user").toString()
        var pw = bundle!!.get("pw").toString()


        //Aggiungo il listener al bottone della nuova prenotazione
        var newPrenBtn = findViewById<Button>(R.id.newPrenDoc)
        newPrenBtn.setOnClickListener {
            // Creo intent per il passaggio
            var intent = Intent(this,prenotazDocActivity::class.java)
            intent.putExtra("user",email)
            intent.putExtra("pw",pw)
            startActivity(intent)
        }

        // Aggiungo il listener al bottone dello storico
        var histBtn = findViewById<Button>(R.id.storPrenDoc)
        histBtn.setOnClickListener {
            //Creo intent per il passaggio
            var histIntent = Intent(this,storicoDocActivity::class.java)
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
    }
}