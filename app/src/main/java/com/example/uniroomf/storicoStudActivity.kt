package com.example.uniroomf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Spinner
import com.android.volley.toolbox.Volley
import com.example.uniroomf.utilityClasses.PrenInfo
import com.example.uniroomf.utilityClasses.ourAdapter.PrenAdapter

class storicoStudActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storico_stud)
    }

    override fun onResume()
    {
        super.onResume()
        val settBtn = findViewById<Button>(R.id.settings)

        fun generaPrenotazioni() : List<PrenInfo>
        {
            var listaElementi : List<PrenInfo>? = null;

            // Request per fare il retrieval delle query




            return listaElementi!!
        }

        var adapter = PrenAdapter(this ,generaPrenotazioni() )
        settBtn.setOnClickListener {
            var aula = findViewById<Spinner>(R.id.spinnerTipologia).textAlignment.toString()
            var docente = findViewById<Spinner>(R.id.spinnerDocente).textAlignment.toString()
            var matST = findViewById<Spinner>(R.id.spinnerMateria).textAlignment.toString()
            var listStorST = findViewById<ListView>(R.id.listaStorStud)
                    listStorST.setAdapter(adapter)


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






        }
    }
