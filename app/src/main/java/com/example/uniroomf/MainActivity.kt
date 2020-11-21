package com.example.uniroomf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.json.JSONArray
import java.io.InputStream
import java.io.OutputStreamWriter
import java.lang.StringBuilder
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.Properties;
import java.net.*;
import android.view.*;
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Imposto i listener per andare alle successive activity - regBtn
        var regBtn =  findViewById<Button>(R.id.regbutt)

        regBtn.setOnClickListener{
             val intent = Intent(this,regActivity::class.java)
            startActivity(intent) //teoricamente cosi dovrei avviare l'altra activity
         }


    }


}
