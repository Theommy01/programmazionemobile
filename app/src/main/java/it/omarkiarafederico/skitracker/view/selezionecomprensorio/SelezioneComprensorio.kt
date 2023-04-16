package it.omarkiarafederico.skitracker.view.selezionecomprensorio

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import it.omarkiarafederico.skitracker.R
import model.Comprensorio

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
            } catch (e: Exception) {
                errorDialog("Impossibile ottenere la lista dei comprensori: " +
                        "${e.message}. Prova a controllare la connessione di rete e la sua " +
                        "disponibilità.")
            }
        }
    }

    private fun errorDialog(msg: String) {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Errore")
        builder.setMessage(msg)
        builder.setPositiveButton("OK") { _, _ ->
            finish()
        }

        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
}