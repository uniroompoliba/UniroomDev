package com.example.uniroomf

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.android.HandlerDispatcher
import kotlinx.coroutines.delay


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}

