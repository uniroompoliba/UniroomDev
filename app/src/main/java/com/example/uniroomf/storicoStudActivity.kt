package com.example.uniroomf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Adapter
import android.widget.Button
import android.widget.ListView
import android.widget.Spinner
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.uniroomf.utilityClasses.PrenInfo
import com.example.uniroomf.utilityClasses.ourAdapter.PrenAdapter2
import org.json.JSONObject
import kotlin.concurrent.thread

class storicoStudActivity : AppCompatActivity () {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storico_stud)
    }

    override fun onResume() {
        super.onResume()
        val setBtn = findViewById<Button>(R.id.settings)

        val bundle2 = intent.extras
        var email = bundle2!!.get("user").toString()
        var pw = bundle2!!.get("password").toString()

        // JSOnObject da inviare
        var datiStud = JSONObject()
        datiStud.put("user",email)
        datiStud.put("pw",pw)

        fun generaPrenotazione(c : JSONObject) : ArrayList<PrenInfo>
        {
            var listaElementi : ArrayList<PrenInfo>? = null;




            return listaElementi!!
        }

        // Request per fare il retrieval delle query
        var myRQ = Volley.newRequestQueue(this)
        var urlPrenStud = "http://uniroompoliba.altervista.org/public/ScriptPrenotazioni/estraiPrenDoc.php"

        var adapter = PrenAdapter2(this, generaPrenotazione(datiStud))
        setBtn.setOnClickListener {
            val aula = findViewById<Spinner>(R.id.spinnerTipologia).textAlignment.toString()
            val docente = findViewById<Spinner>(R.id.spinnerDocente).textAlignment.toString()
            var matST = findViewById<Spinner>(R.id.spinnerMateria).textAlignment.toString()
            var listStorST = findViewById<ListView>(R.id.listaStorStud)
            listStorST.setAdapter(adapter)


            fun setMyHeader()
            {
                var headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                headers.put("Accept", "application/json")
            }
        }

        thread {
            var richiediPrenotazioni = JsonObjectRequest(Request.Method.POST, urlPrenStud, datiStud,
                    Response.Listener { response ->
                // dal JsonObject ricevuto estraggo i dati da aggiungere alla lista
                var stringaPren = response.get("data") // Creo il json string che poi dovrò iterare
                println(stringaPren)


            },
            Response.ErrorListener { error ->
                // Errore
                println(error.toString())
            })

            myRQ.add(richiediPrenotazioni)
        }.start()
    }



    }

