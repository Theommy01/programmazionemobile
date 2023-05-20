package it.omarkiarafederico.skitracker.view.routeTracking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import it.omarkiarafederico.skitracker.R
import model.Comprensorio

class RouteTrackingActivity : AppCompatActivity() {
    private lateinit var mySkiArea: Comprensorio

    override fun onCreate(savedInstanceState: Bundle?) {
        // inizializzazione activity
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_tracking)

        // imposto il titolo all'activity e metto il tasto Indietro nella TitleBar
        supportActionBar?.title = "Selezione pista"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // NOTA: ho utilizzato la versione deprecata del metodo getSerializableExtra perchè in alternativa
        // avrei dovuto mettere come versione minima delle SDK di Android la 13, riducendo drasticamente
        // l'utenza supportata dall'app.
        mySkiArea = intent.getSerializableExtra("selectedSkiArea") as Comprensorio
        supportActionBar?.subtitle = "${mySkiArea.getNome()}, IT"
    }

    fun getSkiArea(): Comprensorio {
        return this.mySkiArea
    }
}