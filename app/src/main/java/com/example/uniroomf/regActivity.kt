package com.example.uniroomf

import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
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

            try
            {
                Thread {

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
                    * Procedura del collegamento col db correttamente effettuata. Ora devo inviare i dati e registrarli nel db
                     */

                    //prendo i dati dalle EditText

                    val nomeUtente : String? = findViewById<EditText>(R.id.NomeR).text.toString().trim()
                    val cognomeUtente : String? = findViewById<EditText>(R.id.cognomeR).text.toString().trim()
                    val emailUtente : String? = findViewById<EditText>(R.id.emailR).text.toString().trim()
                    val pwUtente : String? = findViewById<EditText>(R.id.passwordR).text.toString().trim()
                    val ddnUtente : String? = findViewById<EditText>(R.id.dataNR).text.toString().trim()
                    val matUtente : String? = findViewById<EditText>(R.id.matR).text.toString().trim()

                    //Controlli di validità sull'input
                    if (matUtente!!.length == 7 && ddnUtente!!.length == 10) {
                        //input validi al primo step - procediamo con i controlli di 2 livello
                        /*
                             Controlliamo se è stata inserita una mail corretta, una data di nascita correttamente.
                             Il resto sono stringhe generiche quindi non abbiamo problemi
                             */

                        var res = contSlash(ddnUtente)
                        var resEmail = contEmail(emailUtente)

                        if (res == true || resEmail == true)
                        {
                            if (res == true)
                                Looper.prepare()
                                Toast.makeText(this, "Formato data di nascita non corretto. Troppi / o inseriti dei caratteri non corretti. Riprovare", Toast.LENGTH_LONG)

                            if (resEmail == true)
                                Looper.prepare()
                                Toast.makeText(this, "Formato e-mai non corretto. Troppi @ presenti. Riprovare", Toast.LENGTH_LONG)
                        }
                        else
                        {
                            //Tutti i controlli sono andati a buon fine. Invio i dati e completo la registrazione
                            //Creo il JSonObject
                            val utenti: JSONObject = JSONObject()
                            utenti.put("matricola", matUtente)
                            utenti.put("nome", nomeUtente)
                            utenti.put("cognome", cognomeUtente)
                            utenti.put("email", emailUtente)
                            utenti.put("dataNascita", ddnUtente)
                            utenti.put("password", pwUtente)


                            //Request
                            val inviaDatiServer = JsonObjectRequest(Request.Method.POST, url, utenti,
                                    Response.Listener { response ->
                                        //Toast per indicare che è andato tutto bene
                                        Toast.makeText(this, "Registrazione correttamente avvenuta", Toast.LENGTH_LONG).show()
                                        //Passo alla prossima activity
                                    },
                                    Response.ErrorListener { error ->
                                        Toast.makeText(this,error.toString(),Toast.LENGTH_LONG)
                                    })

                            myRQ.add(inviaDatiServer)

                        }
                    }

                    else
                    {
                        //Input non validi
                            Looper.prepare()
                        Toast.makeText(this,"Input non corretto - Matricola non di 7 caratteri o data di nascita in formato non corretto ",Toast.LENGTH_LONG).show()
                    }

                }.start()

            }
            catch(ex:Exception)
            {
                Toast.makeText(this,ex.toString(),Toast.LENGTH_LONG)
            }


        }
    }


    fun contSlash( s : String?): Boolean {
        //Controllo se la data di nascita è stata inserita correttamente
        var i : Int
        var contSlash = 0
        var contFin : Boolean = false

        for (i in 0..9)
        {
            if (s!!.get(i) == '/')
                contSlash++

            //Controllo che non vengano inserite lettere
            if ((i != 2 || i != 5 ) && (s.get(i) != '0' || s.get(i) != '1' || s.get(i) != '2' || s.get(i) != '2' || s.get(i) != '3' || s.get(i) != '4' || s.get(i) != '5' || s.get(i) != '6' || s.get(i) != '7' ))
                contSlash = 3
        }

        if (contSlash > 2)
            contFin = true

        return contFin
    }

    fun contEmail(s:String?) : Boolean{
        //Controllo se l'email è corretta (al massimo un @)
        var i : Int
        var contAt = 0
        var contFin = false

       for(i in 0..(s!!.length - 1))
       {
            if(s!!.get(i) == '@')
            {
                contAt++
            }
       }

        if (contAt > 1)
            contFin = true

        return contFin
    }




}