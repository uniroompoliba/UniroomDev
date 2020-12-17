package com.example.uniroomf

import android.app.DownloadManager
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.JsonReader
import android.view.ContextThemeWrapper
import android.widget.Button
import android.widget.CalendarView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class prenotazDocActivity : AppCompatActivity() {

    public var tipo = " ";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prenotaz_doc)
    }

    //Funzioni per l'alert
    var lezioneButton = {dialog: DialogInterface, which:Int ->
        tipo = "Lezione"
    }

    var esameButton = { dialog : DialogInterface, which:Int ->
        tipo = "Esame"
    }



    override fun onResume() {
        super.onResume() // Costruttore richiamato dalla superclasse

        //Prendo i valori di username e password passati nel bundle
        val bundle : Bundle? = intent.extras
        var username = bundle!!.getString("user")
        var pw = bundle!!.getString("pw")

        // Prendo i valori degli elementi grafici
        var spinAula = findViewById<Spinner>(R.id.spinnerAule);
        var aula = spinAula.selectedItem.toString()

        var dataPren = findViewById<CalendarView>(R.id.calendPrenS).date.toString();

        var spinOraInizio = findViewById<Spinner>(R.id.spinnerOraInizio)
        var oraInizio = spinOraInizio.selectedItem.toString()

        var spinOraFine = findViewById<Spinner>(R.id.spinnerOraFine)
        var oraFine = spinOraFine.selectedItem.toString()


        // Creo il listener della prenotazione - quando viene premuto il pulsante creo un alert che
        // fa scegliere se si sta inserendo una lezione o un esame

        var insPrenBtn = findViewById<Button>(R.id.invioPrenS)
        insPrenBtn.setOnClickListener{


            // Alert di scelta
            var alertDialogBuilder = AlertDialog.Builder(ContextThemeWrapper(this, R.style.AlertDialogCustom))
            alertDialogBuilder.setTitle("Attenzione!")
            alertDialogBuilder.setMessage("Che tipo di prenotazione si vuole effettuare?")
            alertDialogBuilder.setPositiveButton("Lezione", DialogInterface.OnClickListener(lezioneButton))
            alertDialogBuilder.setNegativeButton("Esame",DialogInterface.OnClickListener(esameButton))

            //Aggiungo l'elemento mancante al jsonObject da inviare
            var datiPren = JSONObject()
            datiPren.put("aula",aula)
            datiPren.put("datazione",dataPren)
            datiPren.put("oraInizio",oraInizio)
            datiPren.put("oraFine",oraFine)
            datiPren.put("tipologia",tipo)
            datiPren.put("user",username)
            datiPren.put("pw",pw)

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
                                Toast.makeText(this,"Prenotazione correttamente effettuata",Toast.LENGTH_LONG).show()

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


        // Reset listener
        var rstBtn = findViewById<Button>(R.id.resPrenS)

        rstBtn.setOnClickListener {

        // Imposto dei valori di partenza

        }

    }

}