package com.example.uniroomf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

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

        var newPrenBtn = findViewById<Button>(R.id.newPrenStud)
        newPrenBtn.setOnClickListener {
            //intent per il passaggio
            var intent = Intent(this, PrenotazStudActivity::class.java)
            intent.putExtra("user",email)
            intent.putExtra("pw",pw)
            startActivity(intent)
        }

        var histBtn = findViewById<Button>(R.id.storPrenStud)
        histBtn.setOnClickListener {
            var intentHis = Intent (this, PrenotazStudActivity::class.java)
            intentHis.putExtra("user",email)
            intentHis.putExtra("pw",pw)
            startActivity(intentHis)
        }

        var settingBtn = findViewById<Button>(R.id.settings)
        settingBtn.setOnClickListener {
            var intentSetting = Intent(this, prenotazStud::class.java)
            intentSetting.putExtra("user", email)
            intentSetting.putExtra("pw", pw)
            startActivity(intentSetting)
        }
    }
}