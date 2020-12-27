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
import com.example.uniroomf.utilityClasses.PrenInfo
import com.example.uniroomf.utilityClasses.ourAdapter.PrenAdapter2
import com.example.uniroomf.utilityClasses.ourAdapter.PrenAdapterStud_InsPren
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.json.JSONObject
import org.w3c.dom.Text
import kotlin.concurrent.thread

class PrenotazStudActivity() : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prenotaz_stud)
    }

    override fun onResume() {
        super.onResume() // Richiamo costruttore superclasse

        // Prendo i valori passati col bundle
        val bundle: Bundle? = intent.extras
        var username = bundle!!.getString("user")
        var pw = bundle!!.getString("pw")

        /*
            In questa activity creo la lista e poi in base al pulsante aggiungo la prenotazione
            Il pulsante lo prendo qui quando faccio la fetch dei vari json object
         */

        // ArrayList necessario
        var listaElementi : ArrayList<PrenInfo> = ArrayList()

        var myRQ = Volley.newRequestQueue(this)
        var urlEstraiPrenEsistenti = "http://uniroompoliba.altervista.org/public/ScriptPrenotazioni/estraiPrenStud_areaPren.php"

        var prendiPrenotazioni = JsonObjectRequest(Request.Method.POST, urlEstraiPrenEsistenti, null,
        Response.Listener { response ->
            // Risposta ottenuta - estrazione
            var dataArray = response.optJSONArray("data")

            for(i in 0 until dataArray.length())
            {
                val temp = dataArray.optJSONObject(i)
                var oggettoLista = PrenInfo()

                // Setto il singolo elemento della lista - i dati vengono presi correttamente
                oggettoLista.setAula(temp.optString("aula"))
                oggettoLista.setOraInizio(temp.optString("oraInizio"))
                oggettoLista.setOraFine(temp.optString("oraFine"))
                oggettoLista.setDataPren(temp.optString("datazione"))

                listaElementi.add(oggettoLista) // Aggiunta alla lista - l'aggiunta alla lista viene effettuata correttamente
            }

            // Creo un'istanza dell'adapter
            var adapter = PrenAdapterStud_InsPren(this, listaElementi)

            // Creo la list view e gli attacco l'adapter
            var listView = findViewById<ListView>(R.id.listaStorDoc)
            listView.setPadding(10,10,10,10)
            listView.adapter = adapter
        },
        Response.ErrorListener { error ->

        })


        // Reset listener
        var rstBtn = findViewById<Button>(R.id.resPrenS)
        rstBtn.setOnClickListener {
            // Imposto dei valori di partenza
        }

    }

}

