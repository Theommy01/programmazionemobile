package it.omarkiarafederico.skitracker.view.routeTracking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import it.omarkiarafederico.skitracker.R

class RouteTrackingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route_tracking)

        supportActionBar?.title = "Tracciamento del percorso"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}