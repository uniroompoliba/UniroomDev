package com.example.uniroomf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.Properties;

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Colleghiamo il database all'activity
        val connectionProps = Properties()
        connectionProps.put("user","uniroompoliba")
        connectionProps.put("password","rktFpXge5rFG")

        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance()
           var conn : Connection? = DriverManager.getConnection("jdbc:mysql:ftp.uniroompoliba.altervista.org:21/",connectionProps)
            print("Connessione correttamente riuscita")
        }
        catch (ex: SQLException )
        {
            ex.printStackTrace();
        }

    }

}