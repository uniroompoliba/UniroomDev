package com.example.uniroomf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import org.json.JSONObject


// Recap singola prenotazione - quando viene finita una prenotazione, fare uscire il recap

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

        // Passo come intent gli elementi presi dalla lista - inutile fare la request

        // Devo convertire la stringa in json object
        val bundlericevuto = intent.extras
        var stringaPren = bundlericevuto!!.getString("datiPrenStringa")
        var ruoloRicevuto = bundlericevuto!!.getString("ruolo")
        var email = bundlericevuto!!.get("email").toString()
        var pw = bundlericevuto!!.get("pw").toString()
        var posto = bundlericevuto!!.get("posto").toString()

        //Creo il jsonObject dalla stringa ricevuta
        var datiPrenInjson = JSONObject(stringaPren)

        //Setto i dati del recap prendendoli dal json
        var aulaRecap = datiPrenInjson.get("aula").toString()
        var dataRecap = datiPrenInjson.get("datazione").toString()
        var oraInizioRecap = datiPrenInjson.get("oraInizio").toString()
        var oraFineRecap = datiPrenInjson.get("oraFine").toString()
        var tipologiaRecap = datiPrenInjson.get("tipologia").toString()
        var postoRecap = datiPrenInjson.get("posto").toString()

        findViewById<TextView>(R.id.aulaValue).setText(aulaRecap)
        findViewById<TextView>(R.id.startValue).setText(oraInizioRecap)
        findViewById<TextView>(R.id.endValue).setText(oraFineRecap)
        findViewById<TextView>(R.id.postoValue).setText(postoRecap)

        if(ruoloRicevuto.equals("Studente"))
        {
            findViewById<TextView>(R.id.postoValue).setText(posto); // Posto settato
        }
        else
        {
            findViewById<TextView>(R.id.postoValue).setText("N/A")
        }



        var okBtn = findViewById<Button>(R.id.okBtn)
        okBtn.setOnClickListener {
            // Se docente, passaggio al menu principale
            if(ruoloRicevuto.equals("Docente"))
            {
                var intentMenuPrinc = Intent(this, menuPrincDoc::class.java)
                intentMenuPrinc.putExtra("user",email)
                intentMenuPrinc.putExtra("pw",pw)
                startActivity(intentMenuPrinc)
            }
            else
            {
                //Se studente, passaggio a suo storico prenotazione
                var intentLista = Intent(this,storicoStudActivity::class.java)

                intentLista.putExtra("user",email)
                intentLista.putExtra("pw",pw)

                // Start dell'activity
                startActivity(intentLista)
            }

        }
    }
}