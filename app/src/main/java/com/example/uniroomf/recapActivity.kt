package com.example.uniroomf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class recapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recap)
        //MODIFICARE SOLO LE LABEL DEL TIPO aulaValue, startValue, endValue,...
        //lA LABEL postoValue HA VALORE "OPZIONALE", PERCHE' DEVI SETTARE IL TESTO DI QUESTA E DELLA LABEL postoTitle A SECONDA DEL RUOLO
        //SE STUDENTE, ALLORA METTI CIO' CHE SERVE E postoTitle RESTA COME E'.
        //SE DOCENTE, ALLORA DEVI CANCELLARLE.
    }
}