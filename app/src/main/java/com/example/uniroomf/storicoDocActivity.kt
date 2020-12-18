package com.example.uniroomf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.uniroomf.utilityClasses.PrenInfo
import com.example.uniroomf.utilityClasses.ourAdapter.PrenAdapter

class storicoDocActivity : AppCompatActivity() {

    fun generaPrenotazioni() : List<PrenInfo>
    {
        var listaElementi : List<PrenInfo>? = null;

        // Request per fare il retrieval delle query




        return listaElementi!!
    }



    // Creo un'istanza dell'adapter
    private var adapter = PrenAdapter(this, generaPrenotazioni())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storico_doc)

        // Creo la list view e gli attacco l'adapter
        var listView = findViewById<ListView>(R.id.listaStorDoc)
        listView.setAdapter(adapter)
    }

    /*
          Qui dobbiamo lavorare gli elementi per lo storico della prenotazione.
          Implementare anche gli onClickListener per i due bottoni, che riguardano rispettivamente
          la modifica e la cancellazione delle singole prenotazioni.
    */


}