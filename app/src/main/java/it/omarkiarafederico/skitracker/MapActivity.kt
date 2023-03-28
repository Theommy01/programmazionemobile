package it.omarkiarafederico.skitracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView


class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        // inizializzazione mappa
        val map : MapView = findViewById(R.id.map)
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setBuiltInZoomControls(true)
        map.setMultiTouchControls(true)
        Configuration.getInstance().userAgentValue = "skitracker"

        // creo un controller della mappa per impostare una posizione iniziale
        val mapController = map.controller
        mapController.setZoom(17)
        val startPoint = GeoPoint(43.909763, 12.912881) // coordinate di piazza del popolo di pesaro
        mapController.setCenter(startPoint)
    }
}