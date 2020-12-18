package com.example.uniroomf

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class prenotazStudActivity : AppCompatActivity() {

    public var tipo = " ";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prenotaz_stud)
    }
    //Funzioni per l'alert
    var lezioneButton = {dialog: DialogInterface, which:Int ->
        tipo = "Lezione"
    }

    var esameButton = { dialog : DialogInterface, which:Int ->
        tipo = "Esame"
    }

    override fun onResume() {
        super.onResume() //costruttore richiamo superclasse
        val bundle : Bundle? = intent.extras
        var username = bundle!!.getString("user")
        var pw = bundle!!.getString("pw")
    }

    //prendo i dati e-o valori dagli elementi grafici
    var spinTipo = findViewById<Spinner>(R.id.spinnerTipologia);
    var type = spinTipo.selectedItem.toString()

    var spinDoc = findViewById<Spinner>(R.id.spinnerDocente)
    var docente = spinDoc.selectedItem.toString()

    var spinMat = findViewById<Spinner>(R.id.spinnerMateria)
    var materia = spinMat.selectedItem.toString()

    var listPrenStud = findViewById<ListView>(R.id.listaInsPrenStud)
    var listStud =listPrenStud.selectedItem.toString()



    // Request - parametri iniziali
    var myRQ = Volley.newRequestQueue(this)
    var urlPrenDoc = "http://uniroompoliba.altervista.org/public/ScriptPrenotazioni/prenDoc.php"

    //Aggiungiamo gli header per accettare la comunicazione json
    fun setMyHeader()
    {
        var headers = HashMap<String, String>()
        headers.put("Content-Type", "application/json")
        headers.put("Accept", "application/json")
    }

    setMyHeader()

    Thread{
        // Request effettiva - da qua
        val inviaPrenDoc = JsonObjectRequest(Request.Method.POST, urlPrenDoc, datiPren,
                Response.Listener { response ->
                    // In caso di risposta corretta apro il recap
                    if (response.toString().equals("Prenotazione correttamente effettuata."))
                    {
                        //Toast message
                        Toast.makeText(this,"Prenotazione correttamente effettuata", Toast.LENGTH_LONG).show()

                        // Apertura activity
                        var intentDoc = Intent(this,recapActivity::class.java)
                        intentDoc.putExtra("ruolo","Docente") // Grazie a questo saprò se far apparire il posto nel recap
                        intentDoc.putExtra("datiPrenStringa",datiPren.toString())
                        startActivity(intentDoc)
                    }

                },
                Response.ErrorListener { error ->
                    println("Errore rilevato: " + error.toString()) // Verifica dell'errore
                })


        //Invio effettivo della richiesta
        myRQ.add(inviaPrenDoc)
    }.start()














}