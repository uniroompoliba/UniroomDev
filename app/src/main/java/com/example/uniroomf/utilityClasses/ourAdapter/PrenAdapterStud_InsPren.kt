package com.example.uniroomf.utilityClasses.ourAdapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.uniroomf.R
import com.example.uniroomf.recapActivity
import com.example.uniroomf.utilityClasses.PrenInfo
import org.json.JSONObject

// Adapter per la riga dello studente nella nuova prenotazione
class PrenAdapterStud_InsPren constructor(var t : Context, p : ArrayList<PrenInfo>, e : String, pw2 : String, r : String): BaseAdapter()
{
    // Elementi dell'adapter
    private var pren : ArrayList<PrenInfo> = p
    private var context : Context? = t
    //seso
    // Elementi studente
    private var emailStud = e
    private var passwordStud = pw2
    private var ruolo = r

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

        // Setto gli elementi di tipologia e docente
        var tipologiaPren = p1!!.findViewById<TextView>(R.id.Tipologia)
        tipologiaPren.setText(prenotazione.getTipo())

        // Prendo il docente
        var docPren = p1!!.findViewById<TextView>(R.id.Docente)
        docPren.setText(prenotazione.getDocente())

        var aggiungiPren = p1!!.findViewById<ImageButton>(R.id.confPren)

        // Una volta preso il bottone, aggiungo il listener per prenotare lo studente
        aggiungiPren.setOnClickListener {
            // Prendo i dati dello studente e della prenotazione

            var datiStud = JSONObject()
            datiStud.put("email",emailStud)
            datiStud.put("pw", passwordStud)

            datiStud.put("aula", prenotazione.getAula().toString())
            datiStud.put("datazione", prenotazione.getDataPren())
            datiStud.put("oraInizio", prenotazione.getOraInizio())
            datiStud.put("oraFine", prenotazione.getOraFine())
            datiStud.put("tipologia", prenotazione.getTipo())

            // Non passo il ruolo perchè mi servirà solo lato client

            var myRQ = Volley.newRequestQueue(context)
            var urlInsPren = "http://uniroompoliba.altervista.org/public/ScriptStudenti/prenStud.php"

            // Request
            Thread{

                var inserisciStud = JsonObjectRequest(Request.Method.POST, urlInsPren, datiStud,
                Response.Listener {response ->

                    // Controllo se la comunicazione è andata a buon fine +
                    if(response.get("tipoErr") == 0)
                    {
                        // OK
                        Toast.makeText(context, "Prenotazione correttamente effettuata", Toast.LENGTH_LONG)

                        // Apro l'activity di recap
                        var apriRecapStud = Intent(context, recapActivity::class.java)

                        // Aggiungo i dati al json object
                        datiStud.put("posto",response.get("Posto"))

                        // Aggiungo i dati necessari
                        apriRecapStud.putExtra("datiPrenStringa", datiStud.toString())
                        apriRecapStud.putExtra("ruolo", ruolo)
                        apriRecapStud.putExtra("email", emailStud)
                        apriRecapStud.putExtra("pw", passwordStud)
                        apriRecapStud.putExtra("posto", response.get("Posto").toString())

                        // Apro l'activity
                        context!!.startActivity(apriRecapStud)
                    }
                    else
                    {
                        Toast.makeText(context, response.get("tipoErr").toString(), Toast.LENGTH_LONG).show()
                    }

                },
                Response.ErrorListener { error ->
                        println("Errore ricevuto: " + error.toString())
                })

                myRQ.add(inserisciStud)
            }.start()


        }

        return p1!!
    }

}