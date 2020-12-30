package com.example.uniroomf.utilityClasses.ourAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.example.uniroomf.R
import com.example.uniroomf.utilityClasses.PrenInfo

class PrenAdapterStud_InsPren constructor(var t : Context, p : ArrayList<PrenInfo>): BaseAdapter()
{
    // Elementi dell'adapter
    private var pren : ArrayList<PrenInfo> = p
    private var context : Context? = t

    override fun getCount(): Int {
        return pren!!.size
    }

    override fun getItemId(p0: Int): Long {
        return pren!!.get(p0).hashCode().toLong()
    }

    override fun getItem(p0: Int): Any {
        return pren!!.get(p0)
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        // Sistemazione della singola view
        var p1 = LayoutInflater.from(context).inflate(R.layout.layout_rowlistview_inserisciprenstud, null);

        // Aggiungo gli elementi della singola riga presi dall'elemento di tipo prenotazione creato in precedenza
        var prenotazione: PrenInfo = getItem(p0) as PrenInfo

        // Setto i singoli elementi della prenotazione
        var testoAula = p1!!.findViewById<TextView>(R.id.nomeAula)
        testoAula.setText(prenotazione.getAula().toString())

        var testoData = p1!!.findViewById<TextView>(R.id.Data)
        testoData.setText(prenotazione.getDataPren())

        var testoOraInizio = p1!!.findViewById<TextView>(R.id.OraInizio)
        testoOraInizio.setText(prenotazione.getOraInizio())

        var testoOraFine = p1!!.findViewById<TextView>(R.id.OraFinale)
        testoOraFine.setText(prenotazione.getOraFine())

        // Aspetto la commit per aggiungere le label di posto e tipologia pren.



        return p1!!
    }

}