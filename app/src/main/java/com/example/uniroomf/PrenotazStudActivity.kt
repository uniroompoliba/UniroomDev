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

class PrenotazStudActivity() : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prenotaz_stud)
    }

    override fun onResume() {
        super.onResume() // Richiamo costruttore superclasse
        val bundle: Bundle? = intent.extras
        var username = bundle!!.getString("user")
        var pw = bundle!!.getString("pw")



        // Reset listener
        var rstBtn = findViewById<Button>(R.id.resPrenS)
        rstBtn.setOnClickListener {
            // Imposto dei valori di partenza
        }

    }

}

