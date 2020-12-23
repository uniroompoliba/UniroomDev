package com.example.uniroomf.utilityClasses.ourAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.uniroomf.R
import com.example.uniroomf.utilityClasses.PrenInfo

// Da fare adapter inserendo il row layout dello stud
class PrenAdapterStud constructor(var t : Context, p : ArrayList<PrenInfo>): BaseAdapter()
{
    // Elementi dell'adapter
    private var pren : ArrayList<PrenInfo> = p
    private var context : Context? = t

    // Elemento di mezzo fra la lista e gli elementi della lista stessa - Funzione base dello storico prenotazioni
    override fun getCount(): Int {
        return pren!!.size
    }

    override fun getItem(p0: Int): Any {
        return pren!!.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return pren!!.get(p0).hashCode().toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        // Setto il layout del singolo elemento
        var p1 = LayoutInflater.from(context).inflate(R.layout.layout_rowlistview_storicostud, null);

        // Aggiungo gli elementi della singola riga presi dall'elemento di tipo prenotazione creato in precedenza
        var prenotazione: PrenInfo = getItem(p0) as PrenInfo

        // Imposto i dati della singola prenotazione
        var testoAula = p1!!.findViewById<TextView>(R.id.nomeAula)
        testoAula.setText(prenotazione.getAula().toString())

        var testoData = p1!!.findViewById<TextView>(R.id.Data)
        testoData.setText(prenotazione.getDataPren())

        var testoOraInizio = p1!!.findViewById<TextView>(R.id.OraInizio)
        testoOraInizio.setText(prenotazione.getOraInizio())

        var testoOraFine = p1!!.findViewById<TextView>(R.id.OraFinale)
        testoOraFine.setText(prenotazione.getOraFine())

        // Come differenza rispetto al docente, dobbiamo settare il posto
        var testoPosto = p1!!.findViewById<TextView>(R.id.Posto)
        testoPosto.setText(prenotazione.getPosto())


        // Effettuo il return della View
        return p1!!
    }
}