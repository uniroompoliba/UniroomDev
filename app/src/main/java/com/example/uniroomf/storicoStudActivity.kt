package com.example.uniroomf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.android.volley.toolbox.Volley

class storicoStudActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storico_stud)
    }

    override fun onResume()
    {
        super.onResume()
        val settBtn = findViewById<Button>(R.id.settings)

        settBtn.setOnClickListener {
            var lezione = findViewById<Spinner>(R.id.spinnerAule).textAlignment.toString()
            var docente = findViewById<Spinner>(R.id.spinnerDocente).textAlignment.toString()
            var matST = findViewById<Spinner>(R.id.spinnerMateria).textAlignment.toString()


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
