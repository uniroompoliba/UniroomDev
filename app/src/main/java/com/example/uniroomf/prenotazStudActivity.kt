package com.example.uniroomf

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import org.w3c.dom.Text

class prenotazStudActivity : AppCompatActivity() {

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
        insPrenBtn.setOnClickListener{
    }


}
















