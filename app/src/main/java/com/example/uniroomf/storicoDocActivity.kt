package com.example.uniroomf

import android.content.Intent
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
import org.json.JSONArray
import org.json.JSONObject

class storicoDocActivity : AppCompatActivity() {

    // Uso l'array list perchè è la variante resizable della lista

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storico_doc)
    }

    override fun onResume() {
        super.onResume()

        //Prendo i dati degli utenti passati tramite bundle
        val bundle2 = intent.extras
        var email = bundle2!!.get("user").toString()
        var pw = bundle2!!.get("password").toString()

        // JSOnObject da inviare
        var datiDoc = JSONObject()
        datiDoc.put("user",email)
        datiDoc.put("pw",pw)

        // Cambio piani. Faccio la request qua dentro e setto l'adapter in base a quello
        var listaElementi : ArrayList<PrenInfo> = ArrayList()
        // Request per fare il retrieval delle query
        var myRQ = Volley.newRequestQueue(this)
        var urlPrenDoc = "http://uniroompoliba.altervista.org/public/ScriptPrenotazioni/estraiPrenDoc.php"

        Thread{

            var richiediPrenotazioni = JsonObjectRequest(Request.Method.POST, urlPrenDoc, datiDoc,
                    Response.Listener { response ->
                        // dal JsonObject ricevuto estraggo i dati da aggiungere alla lista
                        // Devo estrarre i dati dal json
                        var dataArray = response.optJSONArray("data")

                        // I dati vengono presi correttamente - estrazione
                        for(i in 0 until dataArray.length())
                        {
                            val temp = dataArray.optJSONObject(i)
                            var oggettoLista = PrenInfo()

                            // Setto il singolo elemento della lista - i dati vengono presi correttamente
                            oggettoLista.setAula(temp.optString("aula"))
                            oggettoLista.setOraInizio(temp.optString("oraInizio"))
                            oggettoLista.setOraFine(temp.optString("oraFine"))
                            oggettoLista.setDataPren(temp.optString("datazione"))

                            listaElementi.add(oggettoLista) // Aggiunta alla lista - l'aggiunta alla lista viene effettuata correttamente
                        }

                        // Creo un'istanza dell'adapter
                        var adapter = PrenAdapter2(this, listaElementi)

                        // Creo la list view e gli attacco l'adapter
                        var listView = findViewById<ListView>(R.id.listaStorDoc)
                        listView.setPadding(10,10,10,10)
                        listView.adapter = adapter

                    },
                    Response.ErrorListener { error ->
                        // Errore
                        println(error.toString())
                    })

            // Aggiunta alla coda delle richieste
            myRQ.add(richiediPrenotazioni)

        }.start()

    }

    /*
          Qui dobbiamo lavorare gli elementi per lo storico della prenotazione.
          Implementare anche gli onClickListener per i due bottoni, che riguardano rispettivamente
          la modifica e la cancellazione delle singole prenotazioni. Attendo commit grafica di Carnicio
    */


}