package it.omarkiarafederico.skitracker.view.selezionecomprensorio

import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.omarkiarafederico.skitracker.R
import it.omarkiarafederico.skitracker.view.skimap.MapActivity
import model.Comprensorio
import roomdb.RoomHelper
import utility.ALERT_ERROR
import utility.ALERT_INFO
import utility.ApplicationDialog

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

        lateinit var listaComprensori: List<roomdb.Comprensorio>
        skiAreaItemList = ArrayList()

        lifecycleScope.launchWhenCreated {
            try {
                // ottengo la lista dei comprensori dal database
                val dbConnection = RoomHelper().getDatabaseObject(applicationContext)
                listaComprensori = dbConnection.localDatabaseDao().getSkiAreasList()

                // aggiungo i comprensori ottenuti alla lista da visualizzare con la RecyclerView
                supportActionBar?.subtitle = "${listaComprensori.size} comprensori disponibili (paese: IT)"
                for (skiAreaFromDb in listaComprensori) {
                    skiAreaItemList.add(SkiAreaItem(skiAreaFromDb.nome, skiAreaFromDb.id))
                }

                // creo l'Adapter per la RecyclerView
                skiAreaAdapter = SkiAreaAdapter(skiAreaItemList)
                recyclerView.adapter = skiAreaAdapter

                // questo è quello che succede quando faccio click su un elemento della RecyclerView
                skiAreaAdapter.onItemClick = {
                    // creo l'oggetto comprensorio che comprende tutti i dettagli
                    val skiAreaToAdd = Comprensorio(dbConnection.localDatabaseDao().getDettagliComprensorio(it.id))

                    // controllo se il comprensorio che voglio aggiungere è ancora operativo
                    if (skiAreaToAdd.isOperativo()) {
                        // se è operativo lo aggiungo al db
                        val comprensorioPerDB: roomdb.Comprensorio = skiAreaToAdd.convertToEntityClass()

                        // uso un try per evitare che, nel caso andassi ad aggiungere un comprensorio
                        // già esistente, mi causi il crash del programma
                        try {
                            dbConnection.localDatabaseDao().insertNewComprensorio(comprensorioPerDB)
                        } catch (_: SQLiteConstraintException) {}

                        // vado ad indicare l'id del comprensorio selezionato dall'utente
                        dbConnection.localDatabaseDao().modificaComprensorioSelezionato(it.id)

                        // a posto cosi, posso aprire l'activity della mappa
                        finishAffinity()
                        val intent = Intent(applicationContext, MapActivity::class.java)
                        startActivity(intent)
                    } else {
                        // se non è pià operativo avviso l'utente di questo fatto
                        ApplicationDialog().openDialog(
                            ALERT_INFO, "Il comprensorio selezionato non è più " +
                                "aperto al pubblico.", this@SelezioneComprensorio, false)
                    }
                }
            } catch (e: Exception) {
                ApplicationDialog().openDialog(
                    ALERT_ERROR,"Impossibile ottenere la lista dei comprensori: ${e.message}.",
                    this@SelezioneComprensorio, true)
            }
        }
    }

    // Gestione della funzione di ricerca collocata nella parte superiore (ActionBar)
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