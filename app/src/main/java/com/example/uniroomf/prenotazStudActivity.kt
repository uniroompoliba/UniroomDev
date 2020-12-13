package com.example.uniroomf

import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.Spinner
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

class \prenotazStudActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prenotaz_stud)
    }

    override fun onResume() {
        super.onResume() // Costruttore richiamato dalla superclasse

        // Prendo i valori degli elementi grafici
        var spinAula = findViewById<Spinner>(R.id.spinnerAule);
        var aula = spinAula.selectedItem.toString()

        var dataPren = findViewById<CalendarView>(R.id.calendPrenS).date;
        //TODO

        // Reset listener
        var rstBtn = findViewById<Button>(R.id.resPrenS)

        rstBtn.setOnClickListener {
            //Imposto dei valori di partenza

        }

    }
}