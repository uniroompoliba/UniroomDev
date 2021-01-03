package com.example.uniroomf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast

class menuPrincStud : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_princ_stud)
    }

    override fun onResume() {
        super.onResume()

        val bundle = intent.extras
        var email = bundle!!.get("user").toString()
        var pw = bundle!!.get("pw").toString()
        var ruolo = bundle!!.get("ruolo").toString()

        var newPrenBtn = findViewById<Button>(R.id.newPrenStud)
        newPrenBtn.setOnClickListener {
            //intent per il passaggio
            var intent = Intent(this, PrenotazStudActivity::class.java)
            intent.putExtra("user",email)
            intent.putExtra("pw",pw)
            intent.putExtra("ruolo",ruolo)
            startActivity(intent)
        }

        var histBtn = findViewById<Button>(R.id.storPrenStud)
        histBtn.setOnClickListener {
            var intentHis = Intent (this, storicoStudActivity::class.java)
            intentHis.putExtra("user",email)
            intentHis.putExtra("pw",pw)
            startActivity(intentHis)
        }

        var settingBtn = findViewById<Button>(R.id.settings)
        settingBtn.setOnClickListener {
            var intentSetting = Intent(this, acivityImpostazioni::class.java)
            intentSetting.putExtra("user", email)
            intentSetting.putExtra("pw", pw)
            intentSetting.putExtra("ruolo",ruolo)
            startActivity(intentSetting)
        }

        // Setto il pulsante di logout
        var logoutBtn = findViewById<ImageButton>(R.id.logout)
        logoutBtn.setOnClickListener {

            // Passo semplicemente alla schermata iniziale
            var tornaHome = Intent(this, MainActivity::class.java)
            Toast.makeText(this, "Arrivederci.", Toast.LENGTH_LONG)
            startActivity(tornaHome)
        }
    }
}