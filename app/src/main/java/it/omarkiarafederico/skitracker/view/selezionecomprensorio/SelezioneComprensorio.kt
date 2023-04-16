package it.omarkiarafederico.skitracker.view.selezionecomprensorio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import android.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.room.util.appendPlaceholders
import it.omarkiarafederico.skitracker.R
import model.Comprensorio
import okhttp3.OkHttpClient
import okhttp3.Request
import org.w3c.dom.Document
import org.xml.sax.InputSource
import java.io.StringReader
import java.lang.NullPointerException
import java.net.ProtocolException
import java.net.SocketTimeoutException
import javax.xml.parsers.DocumentBuilderFactory

class SelezioneComprensorio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selezione_comprensorio)
        supportActionBar?.subtitle = "Caricamento comprensori..."

        lateinit var listaComprensori:ArrayList<Comprensorio>

        lifecycleScope.launchWhenCreated {
            try {
                listaComprensori = SkiAreaListDownloadThread().getSkiAreaList()

                // visualizzo l'array di comprensori
                supportActionBar?.subtitle = "${listaComprensori.size} comprensori disponibili (paese: IT)"

                val gianfranco = findViewById<TextView>(R.id.textView)
                var luigi = "OK:"
                for (item:Comprensorio in listaComprensori) {
                    luigi += "ID: ${item.getId()} - Nome: ${item.getNome()}  |||   "
                }
                gianfranco.text = luigi
            } catch (e: ProtocolException) {
                val builder = AlertDialog.Builder(applicationContext)
                builder.setTitle("Errore")
                builder.setMessage("Impossibile ottenere la lista dei comprensori: il servizio " +
                        "non è disponibile")
                builder.setPositiveButton("OK") { _, _ ->
                    finish()
                }
                val dialog = builder.create()
                dialog.show()
            } catch (e: SocketTimeoutException) {
                val builder = AlertDialog.Builder(applicationContext)
                builder.setTitle("Errore")
                builder.setMessage("Impossibile ottenere la lista dei comprensori, prova a controllare " +
                        "la connessione di rete (timeout)")
                builder.setPositiveButton("OK") { _, _ ->
                    finish()
                }
                val dialog = builder.create()
                dialog.show()
            }
        }
    }
}