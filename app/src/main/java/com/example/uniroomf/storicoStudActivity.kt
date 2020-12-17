package com.example.uniroomf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class storicoStudActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storico_stud)
    }

    override fun onResume()
    {
        super.onResume()
        val settBtn = findViewById<Button>(R.id.settings)

        settBtn.setOnClickListener {


        }
    }
}