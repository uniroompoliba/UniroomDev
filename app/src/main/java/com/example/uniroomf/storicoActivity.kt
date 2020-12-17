package com.example.uniroomf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button

class storicoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storico)
    }

    override fun onResume()
    {
        super.onResume()
        val settBtn = findViewById<Button>(R.id.settings)

        settBtn.setOnClickListener {


        }
    }
}