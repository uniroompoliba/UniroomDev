package com.example.uniroomf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


// Recap singola prenotazione

class recapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recap)
        //MODIFICARE SOLO LE LABEL DEL TIPO aulaValue, startValue, endValue,...
        //lA LABEL postoValue HA VALORE "OPZIONALE", PERCHE' DEVI SETTARE IL TESTO DI QUESTA E DELLA LABEL postoTitle A SECONDA DEL RUOLO
        //SE STUDENTE, ALLORA METTI CIO' CHE SERVE E postoTitle RESTA COME E'.
        //SE DOCENTE, ALLORA DEVI CANCELLARLE.
    }

    override fun onResume() {
        super.onResume()

        // Passo come intent l'id prenotazione ed effettuo la query. Invio i dati tramite json array

        // Request per la query



        var okBtn = findViewById<Button>(R.id.okBtn)
        okBtn.setOnClickListener {
            //Passaggio alla activity della nuova prenotazione
            var intentMenuPrinc = Intent(this, menuPrincActivity::class.java)
            startActivity(intentMenuPrinc)
        }
    }
}