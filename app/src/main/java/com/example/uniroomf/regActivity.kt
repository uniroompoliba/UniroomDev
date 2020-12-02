package com.example.uniroomf

import android.content.Intent
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
            var intent = Intent(this,princStudActivity::class.java)
            startActivity(intent)
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




