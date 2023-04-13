package it.omarkiarafederico.skitracker.view.tutorial

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import it.omarkiarafederico.skitracker.R
import roomdb.LocalDB
import it.omarkiarafederico.skitracker.view.skimap.MapActivity

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // nascondo la titleBar (che sarebbe posta in alto)
        // (notare il "?", che viene messo per evitare che il programma crashi nel caso la
        // title bar non dovesse essere presente e si avrebbe quindi una NullPointerException)
        supportActionBar?.hide()

        // vado a verificare che l'utente non abbia già visto il tutorial: se si, passo direttamente
        // alla prossima activity
        val db = Room.databaseBuilder(this.applicationContext, LocalDB::class.java, "LocalDatabase").allowMainThreadQueries().build()
        val localDbDao = db.localDatabaseDao()
        val tutorialCompletato = localDbDao.isTutorialCompletato()

        if (tutorialCompletato == 1) {
            // avvio l'activity per la vista mappa
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
    }
}