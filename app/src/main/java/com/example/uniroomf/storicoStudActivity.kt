package com.example.uniroomf

import android.content.Intent
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
import com.example.uniroomf.utilityClasses.ourAdapter.PrenAdapterStud
import org.json.JSONObject
import kotlin.concurrent.thread

class storicoStudActivity : AppCompatActivity () {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        setContentView(R.layout.activity_storico_stud)
        var lista = findViewById<ListView>(R.id.listaStorStud2)

        val bundle2 = intent.extras
        var email = bundle2!!.get("user").toString()
        var pw = bundle2!!.get("pw").toString()

        // Parametri necessari alla comunicazione JSON
        fun setMyHeader()
        {
            var headers = HashMap<String, String>()
            headers.put("Content-Type", "application/json")
            headers.put("Accept", "application/json")
        }
        setMyHeader()

        // JSOnObject da inviare
        var datiStud = JSONObject()
        datiStud.put("user",email)
        datiStud.put("pw",pw)


        // Request per fare il retrieval delle query
        var myRQ = Volley.newRequestQueue(this)
        var urlPrenStud = "http://uniroompoliba.altervista.org/public/ScriptStudenti/estraiPrenStud.php"

        // Cambio piani. Faccio la request qua dentro e setto l'adapter in base a quello
        var listaElementi : ArrayList<PrenInfo> = ArrayList()

        Thread{

            var richiediPrenotazioni = JsonObjectRequest(Request.Method.POST, urlPrenStud, datiStud,
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

                            // Setto il nome del docente
                            var nomeDoc = temp.optString("nomeDoc")
                            var cognomeDoc = temp.optString("cognomeDoc")

                            var datiDoc = nomeDoc + " " + cognomeDoc
                            oggettoLista.setDoc(datiDoc)

                            oggettoLista.setPosto(temp.opt("posto").toString())
                            oggettoLista.setTipo(temp.optString("tipologia"))

                            listaElementi.add(oggettoLista) // Aggiunta alla lista - l'aggiunta alla lista viene effettuata correttamente
                        }

                        // Creo un'istanza dell'adapter
                        var adapter = PrenAdapterStud(this, listaElementi, email, pw, "Studente")

                        // Creo la list view e gli attacco l'adapter

                        lista.adapter = adapter

                    },
                    Response.ErrorListener { error ->
                        // Errore
                        println("Errore storicoStudente: " + error.toString())
                    })

            // Aggiunta alla coda delle richieste
            myRQ.add(richiediPrenotazioni)

        }.start()

        // Listener per ritornare a home
        // Torna a home
        var getHome = findViewById<Button>(R.id.home)

        // Aggiungo il listener
        getHome.setOnClickListener{

            // Creo intent per passaggio home
            var passaHome = Intent(this, menuPrincStud::class.java)

            // Aggiungo gli elementi all'intent
            passaHome.putExtra("user", email)
            passaHome.putExtra("pw",pw)

            // Passaggio effettivo all'activity
            startActivity(passaHome)
        }

        }

    }



