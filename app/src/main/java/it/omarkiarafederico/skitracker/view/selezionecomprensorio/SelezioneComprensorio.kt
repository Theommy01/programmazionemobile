package it.omarkiarafederico.skitracker.view.selezionecomprensorio

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.omarkiarafederico.skitracker.R
import model.Comprensorio

class SelezioneComprensorio : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var skiAreaItemList: ArrayList<SkiAreaItem>
    private lateinit var skiAreaAdapter: SkiAreaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selezione_comprensorio)

        recyclerView = findViewById(R.id.skiAreaRecyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        supportActionBar?.subtitle = "Caricamento comprensori..."

        lateinit var listaComprensori:ArrayList<Comprensorio>
        skiAreaItemList = ArrayList()

        lifecycleScope.launchWhenCreated {
            try {
                listaComprensori = SkiAreaListDownloadThread().getSkiAreaList()

                supportActionBar?.subtitle = "${listaComprensori.size} comprensori disponibili (paese: IT)"
                for (c: Comprensorio in listaComprensori) {
                    skiAreaItemList.add(SkiAreaItem(c.getNome(), c.getId()))
                }

                skiAreaAdapter = SkiAreaAdapter(skiAreaItemList)
                recyclerView.adapter = skiAreaAdapter

                skiAreaAdapter.onItemClick = {
                    val skiArea = it
                    Log.e("AGHFJHDSGDKAHSKHH", "Ski Area Selezionata: ${skiArea.id}")
                }
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