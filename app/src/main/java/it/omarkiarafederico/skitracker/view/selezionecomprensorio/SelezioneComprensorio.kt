package it.omarkiarafederico.skitracker.view.selezionecomprensorio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import it.omarkiarafederico.skitracker.R
import it.omarkiarafederico.skitracker.view.skimap.MapActivity
import model.Comprensorio
import roomdb.LocalDB
import utility.ALERT_ERROR
import utility.ALERT_INFO
import utility.ApplicationAlert

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
                    // creo l'oggetto comprensorio
                    val skiAreaToAdd = Comprensorio(it.id, it.nome)
                    // lo vado a popolare con i dati mancanti, che andrò a prendere online con una
                    // api call
                    skiAreaToAdd.popolateWithOnlineData()

                    if (skiAreaToAdd.getNome() == "NO") {
                        // se mancano dei dati dal JSON ottenuto dall'API, ergo non sono indicati, ad esempio,
                        // numero di piste e/o impianti di risalita...
                        ApplicationAlert().openDialog(
                            ALERT_INFO,
                            "Non è possibile selezionare questo comprensorio: " +
                                    "l'API ha restituito dati incompleti.",
                            this@SelezioneComprensorio,
                            false
                        )
                    } else if (skiAreaToAdd.isOperativo()) {
                        // se è operativo lo aggiungo al db
                        val comprensorioPerDB: roomdb.Comprensorio = skiAreaToAdd.convertToEntityClass()

                        val db = Room.databaseBuilder(applicationContext, LocalDB::class.java,
                            "LocalDatabase").allowMainThreadQueries().build()
                        db.localDatabaseDao().insertNewComprensorio(comprensorioPerDB.id, comprensorioPerDB.nome,
                            comprensorioPerDB.aperto, comprensorioPerDB.numPiste, comprensorioPerDB.numImpianti, comprensorioPerDB.website,
                            comprensorioPerDB.snowpark, comprensorioPerDB.pisteNotturne, comprensorioPerDB.lat, comprensorioPerDB.long,
                            comprensorioPerDB.maxAltitudine, comprensorioPerDB.minAltitudine, comprensorioPerDB.zoom)

                        // vado ad indicare l'id del comprensorio selezionato dall'utente
                        db.localDatabaseDao().modificaComprensorioSelezionato(it.id)

                        // a posto cosi, posso aprire l'activity della mappa
                        finishAffinity()
                        val intent = Intent(applicationContext, MapActivity::class.java)
                        startActivity(intent)
                    } else {
                        // se non è pià operativo avviso l'utente di questo fatto
                        ApplicationAlert().openDialog(
                            ALERT_INFO, "Il comprensorio selezionato non è più " +
                                "aperto al pubblico.", this@SelezioneComprensorio, false)
                    }
                }
            } catch (e: Exception) {
                ApplicationAlert().openDialog(
                    ALERT_ERROR,"Impossibile ottenere la lista dei comprensori: " +
                        "${e.message}. Prova a controllare la connessione di rete e la sua " +
                        "disponibilità.", this@SelezioneComprensorio, true)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.seleziona_comprensorio_search_menu, menu)

        val menuItem: MenuItem? = menu?.findItem(R.id.skiAreaSearchItem)
        val searchView: SearchView = menuItem?.actionView as SearchView
        searchView.queryHint = "Ricerca comprensorio"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null)
                    skiAreaAdapter.filter(p0)
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }
}