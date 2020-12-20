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

class storicoStudActivity : AppCompatActivity () {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storico_stud)
    }

    override fun onResume() {
        super.onResume()
        val setBtn = findViewById<Button>(R.id.settings)
        fun generaPrenotazione() : List<PrenInfo>
        {
            var listaElementi : List<PrenInfo>? = null;




            return listaElementi !!
        }

        var Adapter = PrenAdapter2(this, generaPrenotazione())
        setBtn.setOnClickListener {
            val aula = findViewById<Spinner>(R.id.spinnerTipologia).textAlignment.toString()
            val docente = findViewById<Spinner>(R.id.spinnerDocente).textAlignment.toString()
            var matST = findViewById<Spinner>(R.id.spinnerMateria).textAlignment.toString()
            var listStorST = findViewById<ListView>(R.id.listaStorStud)
            listStorST.setAdapter(Adapter)


            //definire eventi comunicazione
            var url = "http://uniroompoliba.altervista.org/public/accesso.php"
            var myRQ = Volley.newRequestQueue(this)

            fun setMyHeader()
            {
                var headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                headers.put("Accept", "application/json")
            }
        }

        // Request per fare il retrieval delle query
        var myRQ = Volley.newRequestQueue(this)
        var urlPrenDoc = "http://uniroompoliba.altervista.org/public/ScriptPrenotazioni/estraiPrenDoc.php"


    }
}
