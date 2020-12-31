package com.example.uniroomf.utilityClasses.ourAdapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import com.example.uniroomf.R
import com.example.uniroomf.prenotazDocActivity
import com.example.uniroomf.utilityClasses.PrenInfo

class PrenAdapter2 constructor(var t : Context, p : ArrayList<PrenInfo>, e : String, pw2 : String): BaseAdapter()
{
    // Elementi dell'adapter
    private var pren : ArrayList<PrenInfo> = p
    private var context : Context? = t

    // Elementi dell'adapter per l'update della prenotazione
    private var emailDoc2 = e
    private var passwordDoc = pw2


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

      var p1 = LayoutInflater.from(context).inflate(R.layout.layout_rowlistview_storicodoc, null);

        // Aggiungo gli elementi della singola riga presi dall'elemento di tipo prenotazione creato in precedenza
        var prenotazione: PrenInfo = getItem(p0) as PrenInfo

        var testoAula = p1!!.findViewById<TextView>(R.id.nomeAula)
        testoAula.setText(prenotazione.getAula().toString())

        var testoData = p1!!.findViewById<TextView>(R.id.Data)
        testoData.setText(prenotazione.getDataPren())

        var testoOraInizio = p1!!.findViewById<TextView>(R.id.OraInizio)
        testoOraInizio.setText(prenotazione.getOraInizio())

        var testoOraFine = p1!!.findViewById<TextView>(R.id.OraFinale)
        testoOraFine.setText(prenotazione.getOraFine())

        var tipo = p1!!.findViewById<TextView>(R.id.Tipologia)
        tipo.setText(prenotazione.getTipo())

        // Prendo i bottoni

        var modBtn = p1!!.findViewById<ImageButton>(R.id.modificaPren)
        modBtn.setOnClickListener {
            // Modifica della prenotazione - Aprire menu prenotazione con i valori presi dalla prenotazione e poi fare request? -> Proviamo

            // Tecnica provata: Apro il menu prenotazione e passo come bundle dell'intent i dati della prenotazione
            var apriPren = Intent(context, prenotazDocActivity::class.java)

            // Dati della prenotazione
            apriPren.putExtra("aula",prenotazione.getAula().toString())
            apriPren.putExtra("datazione",prenotazione.getDataPren())
            apriPren.putExtra("oraInizio",prenotazione.getOraInizio())
            apriPren.putExtra("oraFine",prenotazione.getOraFine())

            // I dati vengono presi correttamente

            // Aggiungo i dati dell'utente
            apriPren.putExtra("user",emailDoc2)
            apriPren.putExtra("pw",passwordDoc)

            context!!.startActivity(apriPren)  // Passaggio alla activity, vediamo se funziona
        }

        var delPrenBtn = p1!!.findViewById<ImageButton>(R.id.eliminaPren)
        delPrenBtn.setOnClickListener {
            // Elimina prenotazione
        }


        return p1!!
    }


}