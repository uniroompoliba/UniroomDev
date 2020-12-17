package com.example.uniroomf

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class acivityImpostazioni : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()

        // Prendo i dati dal bundle
        val bundleImp = intent.extras
        var ruolo = bundleImp!!.getString("ruolo")
        var user = bundleImp!!.getString("user")
        var pw = bundleImp!!.getString("pw")

        // Request per caricare i dati basata sui dati passati dal login
        var oggettiImp = JSONObject()
        oggettiImp.put("email",user)
        oggettiImp.put("password",pw)
        oggettiImp.put("ruolo",ruolo)

        var myRQ = Volley.newRequestQueue(this)
        var urlRecuperaDati = "http://uniroompoliba.altervista.org/public/utilityScripts/recuperaDatiImpostazioni.php"

        Thread{

            var prendiDati = JsonObjectRequest(Request.Method.POST, urlRecuperaDati, oggettiImp,
            Response.Listener { response ->

                    //Dal JsonObjectRicevuto imposto i dati della settings
                    var nomeEcognome = response.get("Nome").toString() + " " + response.get("Cognome").toString()
                    findViewById<TextView>(R.id.nomeValue).setText(nomeEcognome)
                    findViewById<TextView>(R.id.emailValue).setText(response.get("Email").toString())
                    findViewById<TextView>(R.id.matValue).setText(response.get("Matricola").toString())
                    findViewById<TextView>(R.id.roleValue).setText(ruolo)
                    findViewById<TextView>(R.id.numValue).setText(response.get("numPren").toString())
            },
            Response.ErrorListener { error ->
                        println(error.toString())
            })

            myRQ.add(prendiDati)
        }.start()


        // Aggiungo il listener del cambio password
        var changePwBtn = findViewById<Button>(R.id.modPassw)
        changePwBtn.setOnClickListener {

            // Nuova request di cambio password
            Thread{

                // Invio i dati della nuova password - prima faccio l'alert

            }.start()
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }
}