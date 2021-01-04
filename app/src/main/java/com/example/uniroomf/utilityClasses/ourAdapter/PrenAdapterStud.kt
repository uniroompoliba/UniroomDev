package com.example.uniroomf.utilityClasses.ourAdapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.uniroomf.R
import com.example.uniroomf.storicoStudActivity
import com.example.uniroomf.utilityClasses.PrenInfo
import org.json.JSONObject
import org.w3c.dom.Text

// Adapter per la riga dello storico studente
class PrenAdapterStud constructor(var t : Context, p : ArrayList<PrenInfo>, e : String, pw2 : String, r : String): BaseAdapter()
{
    // Elementi dell'adapter
    private var pren : ArrayList<PrenInfo> = p
    private var context : Context? = t

    // Elementi studente
    private var emailStud = e
    private var passwordStud = pw2
    private var ruolo = r


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

        // Settaggio dati docente e posto e tipologia

        var testoDoc = p1!!.findViewById<TextView>(R.id.Docente)
        testoDoc.setText(prenotazione.getDocente())

        var testoPosto = p1!!.findViewById<TextView>(R.id.Posto)
        testoPosto.setText(prenotazione.getPosto())

        var testoTipo = p1!!.findViewById<TextView>(R.id.Tipologia)
        testoTipo.setText(prenotazione.getTipo())

        // Listener del pulsante per eliminare la prenotazione dello studente
        var eliminaPren = p1!!.findViewById<ImageButton>(R.id.eliminaPren)

        eliminaPren.setOnClickListener {
            // Prendo i dati della prenotazione

            var datiStud = JSONObject()
            datiStud.put("email",emailStud)
            datiStud.put("pw", passwordStud)

            datiStud.put("aula", prenotazione.getAula().toString())
            datiStud.put("datazione", prenotazione.getDataPren())
            datiStud.put("oraInizio", prenotazione.getOraInizio())
            datiStud.put("oraFine", prenotazione.getOraFine())
            datiStud.put("tipologia", prenotazione.getTipo())

            var myRQ = Volley.newRequestQueue(context)
            var urlDelPren = "http://uniroompoliba.altervista.org/public/ScriptStudenti/delPrenStud.php"

            Thread{
                var reqDel = JsonObjectRequest(Request.Method.POST, urlDelPren, datiStud,
                        Response.Listener {response ->
                            if(response.get("tipoErr").equals(0))
                            {
                                // Comunicazione andata a buon fine
                                Toast.makeText(context, "Prenotazione correttamente eliminata.", Toast.LENGTH_SHORT).show()

                                // Passo  allo storico
                                var mioIntent = Intent(context, storicoStudActivity::class.java)

                                mioIntent.putExtra("user", emailStud)
                                mioIntent.putExtra("pw",passwordStud)

                                context!!.startActivity(mioIntent)

                            }
                        } ,
                        Response.ErrorListener { error ->
                            println("Errore PrenAdapterStud: " + error.toString())
                        })

                myRQ.add(reqDel)
            }.start()
            

        }

        // Effettuo il return della View
        return p1!!
    }
}