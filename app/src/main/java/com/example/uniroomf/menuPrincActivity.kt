package com.example.uniroomf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class menuPrincActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_princ)
    }

    override fun onResume() {
        super.onResume() // Costruttore richiamato dalla super classe
        val bundle : Bundle? = intent.extras
        var ruolo = bundle!!.getString("ruolo")


        // Aggiungo il listener della nuova prenotazione
        var addPren = findViewById<Button>(R.id.newPren)
        //Passo direttamente alla prenotazione. Il discorso del ruolo lo vedremo più tardi, quando
        //andremo a fare il recap!
        addPren.setOnClickListener {
            //Passaggio alla activity della nuova prenotazione
            var intentPassNewPren = Intent(this,PrenotazStudActivity::class.java)
            startActivity(intentPassNewPren)
        }


        //Aggiungo il listener al bottone dello storico
        var histBtn = findViewById<Button>(R.id.storPren)

        histBtn.setOnClickListener {
            // Passaggio all'activity dello storico
            var intentPassHist = Intent(this,storicoStudActivity::class.java)
            intentPassHist.putExtra("ruolo",ruolo)
            startActivity(intentPassHist)
        }

        //Aggiungo il listener al bottone delle impostazioni
        var settBtn = findViewById<Button>(R.id.settings)

        settBtn.setOnClickListener {
            //Passaggio alla activity delle impostazioni
            var intentPassImp = Intent(this,acivityImpostazioni::class.java)
            intentPassImp.putExtra("ruolo",ruolo)
            startActivity(intentPassImp)
        }
    }
}