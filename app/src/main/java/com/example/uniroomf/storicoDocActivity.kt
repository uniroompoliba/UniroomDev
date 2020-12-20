package com.example.uniroomf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ListView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.uniroomf.utilityClasses.PrenInfo
import com.example.uniroomf.utilityClasses.ourAdapter.PrenAdapter2
import org.json.JSONObject

class storicoDocActivity : AppCompatActivity() {


//Aggiungere List come return tipo
    fun generaPrenotazioni(c : JSONObject) : List<PrenInfo>
    {

        var listaElementi : List<PrenInfo>? = null

        // Request per fare il retrieval delle query
        var myRQ = Volley.newRequestQueue(this)
        var urlPrenDoc = "http://uniroompoliba.altervista.org/public/ScriptPrenotazioni/estraiPrenDoc.php"


        Thread{

        var richiediPrenotazioni = JsonObjectRequest(Request.Method.POST, urlPrenDoc, c,
        Response.Listener { response ->
            // dal JsonObject ricevuto estraggo i dati da aggiungere alla lista
            var stringaPren = response.get("data").toString() // Creo il json string che poi dovrò iterare
            println(stringaPren)
            var pren = JSONObject(stringaPren)
            var mioArray = pren.optJSONArray("data")

                          // Proviamo a estrarre i valori delle prenotazioni
            for(i in 0 until mioArray.length() - 1)
            {
                var tempJson = mioArray.getJSONObject(i)
                var aula = tempJson.opt("aula").toString()
                println(aula)
            }


        },
        Response.ErrorListener { error ->
                // Errore
                println(error.toString())
        })

            myRQ.add(richiediPrenotazioni)
        }.start()

        return listaElementi!!
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storico_doc)

        //Prendo i dati degli utenti passati tramite bundle
        val bundle2 = intent.extras
        var email = bundle2!!.get("user").toString()
        var pw = bundle2!!.get("password").toString()

        // JSOnObject da inviare
        var datiDoc = JSONObject()
        datiDoc.put("user",email)
        datiDoc.put("pw",pw)

        //Aggiungere dati a jsonObject
        // Creo un'istanza dell'adapter
        //var adapter = PrenAdapter2(this, generaPrenotazioni(datiDoc))
        generaPrenotazioni(datiDoc)

        // Creo la list view e gli attacco l'adapter
        var listView = findViewById<ListView>(R.id.listaStorDoc)
        // listView.setAdapter(adapter)
    }

    override fun onResume() {
        super.onResume()


    }

    /*
          Qui dobbiamo lavorare gli elementi per lo storico della prenotazione.
          Implementare anche gli onClickListener per i due bottoni, che riguardano rispettivamente
          la modifica e la cancellazione delle singole prenotazioni.
    */


}