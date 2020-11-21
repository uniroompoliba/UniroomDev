package com.example.uniroomf

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.lang.String.format
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.*;
import java.net.URLConnection
import java.net.URLEncoder

class regActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg)
    }

    override fun onResume()
    {
        super.onResume()

        //Codice per il reset dei campi del form
        val rstBtn = findViewById<Button>(R.id.resR)

        rstBtn.setOnClickListener {
            //Cliccato il bottone del reset, imposto i campi a 0
            findViewById<EditText>(R.id.NomeR).setText("Nome")
            findViewById<EditText>(R.id.cognomeR).setText("Cognome")
            findViewById<EditText>(R.id.emailR).setText(" ")
            findViewById<EditText>(R.id.passwordR).setText(" ")
            findViewById<EditText>(R.id.dataNR).setText(" ")
            findViewById<EditText>(R.id.matR).setText(" ")
        }

        //Listener per la registrazione nel DB
        val regBtn = findViewById<Button>(R.id.regR)

        regBtn.setOnClickListener {

            //Avvio l'esecuzione in un nuovo thread poichè nel main thread non può andare
            Thread {
                try
                {
                    //riproviamo con un tutorial più completo con httpUrlConnection consigliato da francesco paolo\
                    var url = "http://uniroompoliba.altervista.org/public/genReg.php"
                    var myRQ = Volley.newRequestQueue(this)

                    //Aggiungiamo gli header per accettare la comunicazione json
                    fun setMyHeader()
                    {
                       var headers = HashMap<String, String>()
                        headers.put("Content-Type", "application/json")
                        headers.put("Accept", "application/json")
                    }

                    setMyHeader()

                    /*
                    * Procedura del collegamento col db correttamente effettuata. Ora devo sistemare il parseNetwork
                    * per prendere i dati anche se nulli
                    * */
                    val sReq = StringRequest(Request.Method.POST,url,
                    Response.Listener<String> { response ->
                        findViewById<EditText>(R.id.NomeR).setText(response.toString())

                    },
                            Response.ErrorListener { error: VolleyError? ->
                                //In caso di errore
                                println("Errore: " + error.toString())
                            }
                            )

                    myRQ.add(sReq)

                }
                catch (ex : Exception)
                {
                    println("Errori rilevati: " + ex.printStackTrace())
                }

            }.start()


        }
    }





}