package com.example.uniroomf

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.text.DateFormat
import java.util.*
import kotlin.collections.HashMap


class prenotazDocActivity : AppCompatActivity() {

    public var tipo = " ";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prenotaz_doc)

    }


    override fun onResume() {
        super.onResume() // Costruttore richiamato dalla superclasse

        // Dichiaro una variabile che mi permette di capire se devo effettuare l'update della prenotazione
        var updating = 0

        //Prendo i valori di username e password passati nel bundle
        val bundle : Bundle? = intent.extras
        var username = bundle!!.getString("user")
        var pw = bundle!!.getString("pw")

        // Prendo i valori eventualmente passati dallo storico necessari per fare l'update
        var aulaRicevuta = bundle!!.getString("aula")
        var datazioneRicevuta = bundle!!.getString("datazione")
        var oraInizioRicevuta = bundle!!.getString("oraInizio")
        var oraFineRicevuta = bundle!!.getString("oraFine")

        // Serie di controlli per settare gli spinner in caso di modifica dallo storico

        // Settaggio valore conosciuto aula
        if (aulaRicevuta != null)
        {
            var spinAula = findViewById<Spinner>(R.id.spinnerAule)

            // Prendo l'indice della lettera
            var arrayAule = resources.getStringArray(R.array.Aule)

            var letterIndex = 0
            var i = 0

            for(i in 0 until arrayAule.size)
            {
                if (arrayAule.get(i).equals(aulaRicevuta))
                {
                    letterIndex = i
                }
            }
            spinAula.setSelection(letterIndex)
        }

        // Settaggio valore conosciuto calendario
        if(datazioneRicevuta != null)
        {
            println("Datazione ricevuta: " + datazioneRicevuta)
            var dataSplittata = datazioneRicevuta.split("/")


            // Inserisco il valore dello spinner del giorno
            var arrayGiorno = resources.getStringArray(R.array.Giorno)

            var letterIndex = 0
            var i = 0

            for(i in 0 until dataSplittata.size)
            {
                println(dataSplittata.get(i))
            }

            // Ciclo per prendere l'indice per settare lo spinner
            for(i in 0 until arrayGiorno.size)
            {
                if(arrayGiorno.get(i).equals(dataSplittata.get(0)))
                {
                    letterIndex = i
                }
            }

            // Setto lo spinner del giorno
            findViewById<Spinner>(R.id.spinnerGiorno).setSelection(letterIndex)

            // Inserisco il valore per lo spinner del mese
            var arrayMese = resources.getStringArray(R.array.Mese)

            letterIndex = 0
            i = 0

            // Ciclo per prendere l'indice per lo spinner
            for(i in 0 until arrayMese.size)
            {
                if(arrayMese.get(i).equals(dataSplittata.get(1)))
                {
                    letterIndex = i
                }
            }

            findViewById<Spinner>(R.id.spinnerMese).setSelection(letterIndex)

            // Inserisco il valore per lo spinner dell'anno
            var arrayAnno = resources.getStringArray(R.array.Anno)

            // Setto le variabili di utility a 0
            letterIndex = 0
            i = 0

            // Ciclo per prendere l'indice per settare lo spinner
            for(i in 0 until arrayAnno.size)
            {
                if(arrayAnno.get(i).equals(dataSplittata.get(2)))
                {
                    letterIndex = i
                }
            }

            // Setto lo spinner dell'anno
            findViewById<Spinner>(R.id.spinnerAnno).setSelection(letterIndex)
        }
        else
        {
            Toast.makeText(this,"Data vuota!",Toast.LENGTH_LONG)
        }

        // Settaggio valore conosciuto ora Inizio


        // Bottone da modellare
        var insPrenBtn = findViewById<Button>(R.id.invioPrenS)

        if(aulaRicevuta != null)
        {
            // Devo effettuare un aggiornamento
            insPrenBtn.setText("Aggiorna")
            updating = 1
        }

        // Creo il listener della prenotazione - quando viene premuto il pulsante creo un alert che
        // fa scegliere se si sta inserendo una lezione o un esame

        insPrenBtn.setOnClickListener{

            // Prendo i valori degli elementi grafici
            var spinAula = findViewById<Spinner>(R.id.spinnerAule);
            var aula = spinAula.selectedItem.toString()

            // Spinner calendario
            var giornoPren = findViewById<Spinner>(R.id.spinnerGiorno).selectedItem.toString()
            var mesePren = findViewById<Spinner>(R.id.spinnerMese).selectedItem.toString()
            var annoPren = findViewById<Spinner>(R.id.spinnerAnno).selectedItem.toString()

            var dataPren = giornoPren + "/" + mesePren + "/" + annoPren

            // Spinner ora inizio
            var spinOraInizio = findViewById<Spinner>(R.id.spinnerOraInizio)
            var oraInizio = spinOraInizio.selectedItem.toString()

            // Spinner ora fine
            var spinOraFine = findViewById<Spinner>(R.id.spinnerOraFine)
            var oraFine = spinOraFine.selectedItem.toString()


            // Alert di scelta
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Cosa si sta inserendo?")
            builder.setMessage("Inserire tipo di prenotazione")

            builder.setPositiveButton("Lezione"){ dialog, which: Int ->
                    tipo = "Lezione"

                // Il programma deve continuare solo se è stato inserito il tipo
                if (tipo != " " && updating == 0)
                {
                    //Aggiungo l'elemento mancante al jsonObject da inviare
                    var datiPren = JSONObject()
                    datiPren.put("aula", aula)
                    datiPren.put("datazione", dataPren)
                    datiPren.put("oraInizio", oraInizio)
                    datiPren.put("oraFine", oraFine)
                    datiPren.put("tipologia", tipo)
                    datiPren.put("user", username)
                    datiPren.put("pw", pw)

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
                                    if (response.get("tipoErr").toString().equals("Prenotazione correttamente effettuata.")) {
                                        //Toast message
                                        Toast.makeText(this, "Prenotazione correttamente effettuata", Toast.LENGTH_LONG).show()

                                        // Apertura activity
                                        var intentDoc = Intent(this, recapActivity::class.java)
                                        intentDoc.putExtra("ruolo", "Docente") // Grazie a questo saprò se far apparire il posto nel recap
                                        intentDoc.putExtra("datiPrenStringa", datiPren.toString())
                                        intentDoc.putExtra("email", username)
                                        intentDoc.putExtra("pw", pw)
                                        startActivity(intentDoc)
                                    } else {
                                        // Prenotazione non andata a buon fine
                                        Toast.makeText(this, response.get("tipoErr").toString(), Toast.LENGTH_LONG).show()
                                    }

                                },
                                Response.ErrorListener { error ->
                                    println("Errore rilevato: " + error.toString()) // Verifica dell'errore
                                })


                        //Invio effettivo della richiesta
                        myRQ.add(inviaPrenDoc)
                    }.start()

                }
                else if (tipo != " " && updating == 1)
                {
                    // Devo effettuare l'update, ho bisogno di effettuare una query diversa - Update per il tipo prenotazione = "Lezione"

                    //Aggiungo l'elemento mancante al jsonObject da inviare
                    var datiPren = JSONObject()
                    datiPren.put("aula", aula)
                    datiPren.put("datazione", dataPren)
                    datiPren.put("oraInizio", oraInizio)
                    datiPren.put("oraFine", oraFine)
                    datiPren.put("tipologia", tipo)
                    datiPren.put("user", username)
                    datiPren.put("pw", pw)

                    // Aggiunta dei dati ricevuti dallo storico
                    datiPren.put("aulaRicevuta", aulaRicevuta)
                    datiPren.put("datazioneRicevuta", datazioneRicevuta)
                    datiPren.put("oraInizioRicevuta", oraInizioRicevuta)
                    datiPren.put("oraFineRicevuta", oraFineRicevuta)

                    // Request - parametri iniziali
                    var myRQ = Volley.newRequestQueue(this)
                    var urlUpdate = "http://uniroompoliba.altervista.org/public/ScriptPrenotazioni/aggiornaPren.php"

                    //Aggiungiamo gli header per accettare la comunicazione json
                    fun setMyHeader()
                    {
                        var headers = HashMap<String, String>()
                        headers.put("Content-Type", "application/json")
                        headers.put("Accept", "application/json")
                    }

                    setMyHeader()

                    Thread {

                        // Richiesta di update - uso i dati ricevuti dallo storico per effettuare l'update

                        var aggiornaPren = JsonObjectRequest(Request.Method.POST, urlUpdate, datiPren,
                                Response.Listener { response ->
                                    println("Risposta ricevuta: " + response.toString())
                                    // Update completato, torno allo storic
                                    if (response.get("tipoErr").toString() == "0") {
                                        Toast.makeText(this, "Prenotazione correttamente aggiornata!", Toast.LENGTH_LONG).show()

                                        var tornaAlloStorico = Intent(this, storicoDocActivity::class.java)
                                        tornaAlloStorico.putExtra("user", username)
                                        tornaAlloStorico.putExtra("password", pw)

                                        startActivity(tornaAlloStorico)
                                    }
                                },
                                Response.ErrorListener { error ->
                                    println("Errore rilevato: " + error.toString())

                                })
                        myRQ.add(aggiornaPren)
                    }.start()
                }
            }

            builder.setNegativeButton("Esame"){ dialog, which: Int ->
                tipo = "Esame"

                // Il programma deve continuare solo se è stato inserito il tipo
                if (tipo != " " && updating == 0)
                {
                    //Aggiungo l'elemento mancante al jsonObject da inviare
                    var datiPren = JSONObject()
                    datiPren.put("aula", aula)
                    datiPren.put("datazione", dataPren)
                    datiPren.put("oraInizio", oraInizio)
                    datiPren.put("oraFine", oraFine)
                    datiPren.put("tipologia", tipo)
                    datiPren.put("user", username)
                    datiPren.put("pw", pw)

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
                                    if (response.get("tipoErr").toString().equals("Prenotazione correttamente effettuata.")) {
                                        //Toast message
                                        Toast.makeText(this, "Prenotazione correttamente effettuata", Toast.LENGTH_LONG).show()

                                        // Apertura activity
                                        var intentDoc = Intent(this, recapActivity::class.java)
                                        intentDoc.putExtra("ruolo", "Docente") // Grazie a questo saprò se far apparire il posto nel recap
                                        intentDoc.putExtra("datiPrenStringa", datiPren.toString())
                                        intentDoc.putExtra("email", username)
                                        intentDoc.putExtra("pw", pw)
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
                else if(tipo != " " && updating == 1)
                {
                    // Devo effettuare l'update, ho bisogno di effettuare una query diversa - Update per il tipo prenotazione = "Esame"

                    //Aggiungo l'elemento mancante al jsonObject da inviare
                    var datiPren = JSONObject()
                    datiPren.put("aula", aula)
                    datiPren.put("datazione", dataPren)
                    datiPren.put("oraInizio", oraInizio)
                    datiPren.put("oraFine", oraFine)
                    datiPren.put("tipologia", tipo)
                    datiPren.put("user", username)
                    datiPren.put("pw", pw)

                    // Aggiunta dei dati ricevuti dallo storico
                    datiPren.put("aulaRicevuta", aulaRicevuta)
                    datiPren.put("datazioneRicevuta", datazioneRicevuta)
                    datiPren.put("oraInizioRicevuta", oraInizioRicevuta)
                    datiPren.put("oraFineRicevuta", oraFineRicevuta)

                    // Request - parametri iniziali
                    var myRQ = Volley.newRequestQueue(this)
                    var urlUpdate = "http://uniroompoliba.altervista.org/public/ScriptPrenotazioni/aggiornaPren.php"

                    //Aggiungiamo gli header per accettare la comunicazione json
                    fun setMyHeader()
                    {
                        var headers = HashMap<String, String>()
                        headers.put("Content-Type", "application/json")
                        headers.put("Accept", "application/json")
                    }

                    setMyHeader()

                    Thread {

                        // Richiesta di update - uso i dati ricevuti dallo storico per effettuare l'update

                        var aggiornaPren = JsonObjectRequest(Request.Method.POST, urlUpdate, datiPren,
                                Response.Listener { response ->
                                    // Update completato, torno allo storic
                                    if (response.get("tipoErr").toString() == "0") {
                                        Toast.makeText(this, "Prenotazione correttamente aggiornata!", Toast.LENGTH_LONG).show()

                                        var tornaAlloStorico = Intent(this, storicoDocActivity::class.java)
                                        tornaAlloStorico.putExtra("user", username)
                                        tornaAlloStorico.putExtra("password", pw)

                                        startActivity(tornaAlloStorico)
                                    }
                                },
                                Response.ErrorListener { error ->
                                    println("Errore rilevato: " + error.toString())

                                })

                        myRQ.add(aggiornaPren)
                    }.start()
                }
            }

            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
        }


        // Reset listener
        var rstBtn = findViewById<Button>(R.id.resPrenS)

        rstBtn.setOnClickListener {

        // Imposto dei valori di partenza
            findViewById<Spinner>(R.id.spinnerAule).setSelection(0) // Setto il valore dello spinner alla lettera A

            findViewById<Spinner>(R.id.spinnerGiorno).setSelection(0) // Setto il valore dello spinner al giorno 01
            findViewById<Spinner>(R.id.spinnerMese).setSelection(0) // Setto il valore dello spinner a Gennaio
            findViewById<Spinner>(R.id.spinnerAnno).setSelection(0) // Setto il valore dello spinner a 2020

            // Setto ora di inizio e fine ai valori di default
            findViewById<Spinner>(R.id.spinnerOraInizio).setSelection(0) // Setto il valore dell'ora inizio a 8:30
            findViewById<Spinner>(R.id.spinnerOraFine).setSelection(0)   // Setto il valore dell'ora fine a 11:00

        }
    }

}