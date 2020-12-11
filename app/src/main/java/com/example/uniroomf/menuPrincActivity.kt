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

        //Controllo se aprire la prenotazione di uno studente o di un docente
        if(ruolo.equals("Studente"))
        {
            // Devo aprire la prenotazione dello Studente

            addPren.setOnClickListener {
                //Passaggio alla activity della nuova prenotazione
                var intentPassNewPren = Intent(this,prenotazStudActivity::class.java)
                startActivity(intentPassNewPren)
            }

        }
        else
        {
            //Devo aprire la prenotazione del docente

           //TODO
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