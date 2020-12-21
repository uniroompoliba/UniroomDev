package com.example.uniroomf

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.ContextThemeWrapper
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.json.JSONObject
import org.w3c.dom.Text
import kotlin.concurrent.thread

class PrenotazStudActivity() : AppCompatActivity(), Parcelable {

    var tipo = " ";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prenotaz_stud)
    }

    //Funzioni per l'alert
    var lezioneButton = { dialog: DialogInterface, which: Int ->
        tipo = "Lezione"
    }

    var esameButton = { dialog: DialogInterface, which: Int ->
        tipo = "Esame"
    }

    constructor(parcel: Parcel) : this() {
        tipo = parcel.readString()
    }

    override fun onResume() {
        super.onResume() //costruttore richiamo superclasse
        val bundle: Bundle? = intent.extras
        var username = bundle!!.getString("user")
        var pw = bundle!!.getString("pw")

        //prendo i dati e-o valori dagli elementi grafici
        var spinTipo = findViewById<Spinner>(R.id.spinnerTipologia);
        var tpe = spinTipo.selectedItem.toString()

        var spinDoc = findViewById<Spinner>(R.id.spinnerDocente)
        var docente = spinDoc.selectedItem.toString()

        var spinMat = findViewById<Spinner>(R.id.spinnerMateria)
        var materia = spinMat.selectedItem.toString()

        var listPrenStud = findViewById<ListView>(R.id.listaInsPrenStud).listPaddingTop.toString()

        var insPrenBtn = findViewById<Button>(R.id.invioPrenS)
        insPrenBtn.setOnClickListener {
            var alertDialogBuilder = AlertDialog.Builder(ContextThemeWrapper(this, R.style.AlertDialogCustom))
            alertDialogBuilder.setTitle("Attenzione!")
            alertDialogBuilder.setMessage("Che tipo di prenotazione di vuole scegliere?")
            alertDialogBuilder.setPositiveButton("Lezione", DialogInterface.OnClickListener(lezioneButton))
            alertDialogBuilder.setNegativeButton("esame", DialogInterface.OnClickListener(esameButton))
            alertDialogBuilder.show()

            if (tipo != " ") {
                var datiPren = JSONObject()
                datiPren.put("tipologia", tpe)
                datiPren.put("docente", docente)
                datiPren.put("materia", materia)
                datiPren.put("user", username)
                datiPren.put("pw", pw)


                var myRQ = Volley.newRequestQueue(this)
                var urlPrenStud = "http://uniroompoliba.altervista.org/public/ScriptPrenotazioni/prenDoc.php"

                fun setMyHeader() {
                    var headers = HashMap<String, String>()
                    headers.put("Content-Type", "application/json")
                    headers.put("Accept", "application/json")
                }

                setMyHeader()

                thread {
                    val inviaPrenStud = JsonObjectRequest(Request.Method.POST, urlPrenStud, datiPren,
                            Response.Listener { response ->

                                if (response.get("tipoErr").toString().equals("Prenotazione correttamente effettuata.")) {
                                    Toast.makeText(this, "", Toast.LENGTH_LONG).show()

                                    var intentStud = Intent(this, recapActivity::class.java)
                                    intentStud.putExtra("ruolo", "Docente") // Grazie a questo saprò se far apparire il posto nel recap
                                    intentStud.putExtra("datiPrenStringa", datiPren.toString())
                                    intentStud.putExtra("email", username)
                                    intentStud.putExtra("pw", pw)
                                    startActivity(intentStud)
                                }

                            },
                            Response.ErrorListener { error ->
                                print("ERRORE RILEVATO: " + error.toString())
                            })

                    myRQ.add(inviaPrenStud)
                }.start()
            }


        }


        // Reset listener
        var rstBtn = findViewById<Button>(R.id.resPrenS)

        rstBtn.setOnClickListener {

            // Imposto dei valori di partenza

        }

    }

}