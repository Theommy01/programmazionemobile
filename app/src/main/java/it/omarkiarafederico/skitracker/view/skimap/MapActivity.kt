package it.omarkiarafederico.skitracker.view.skimap

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import it.omarkiarafederico.skitracker.R
import it.omarkiarafederico.skitracker.view.tutorial.WelcomeActivity
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


class MapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding //da ritoccare il gradle su buildFeatures
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        // inizializzazione mappa
        val map : MapView = findViewById(R.id.map)
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)
        map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
        map.isTilesScaledToDpi = true
        Configuration.getInstance().userAgentValue = "skitracker"

        // creo un controller della mappa per impostare una posizione iniziale
        val mapController = map.controller
      //  val startPoint = GeoPoint(46.370066950988, 10.659417137504)
      //  mapController.setCenter(startPoint)
      //  mapController.animateTo(startPoint, 16.0, 1200)

        // aggiungo la possibilità di poter ruotare la mappa con due dita
        val mRotationGestureOverlay = RotationGestureOverlay(map)
        mRotationGestureOverlay.setEnabled(true)
        map.getOverlays().add(mRotationGestureOverlay)

        // aggiungo la barra della scala della dimensione in km reali nella mappa
        val scaleBarOverlay = ScaleBarOverlay(map)
        scaleBarOverlay.setCentred(true)
        scaleBarOverlay.setScaleBarOffset(200, 10)
        map.overlays.add(scaleBarOverlay)

        // aggiungo la geolocalizzazione dell'user

        val ctx: Context = applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))

        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setBuiltInZoomControls(true)
        map.setMultiTouchControls(true)

        mapController.setZoom(14L.toDouble())
        val startPoint = GeoPoint(android.R.attr.x, android.R.attr.y)
        mapController.animateTo(startPoint)

        val mLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(ctx), map)
        mLocationOverlay.enableMyLocation()
        map.getOverlays().add(mLocationOverlay)

        mapController.setCenter(startPoint)
        mapController.animateTo(startPoint, 16.0, 1200)

        //aggiunta di un marker

        val startMarker = Marker(map)
        startMarker.position = startPoint
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        map.getOverlays().add(startMarker)

        //configurazione bottom navigation bar

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.setOnItemReselectedListener {
            val item: MenuItem
            val id = item.itemId
            when(id) {
            //    R.id.cronologia -> replaceFragment(Cronologia())
            }
        }

    }

    //creazione menu a tendina
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = getMenuInflater()
        inflater.inflate(R.menu.optionsmenu, menu)
        return true
    }

    //configurazione menù a tendina

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        when(id) { //nel when, si ricorda che si possono mettere solo le costanti

            R.id.item1 -> {
                return true
            }

            R.id.item2 -> {
                return true
            }

            R.id.item3 -> { //ritorno alla activity del tutorial
                val intent = Intent(this, WelcomeActivity::class.java)
                startActivity(intent)
                return true
            }

            R.id.item4 -> { //indirizzamento all'activity "ulteriori informazioni"
                val intent = Intent(this, AboutUsActivity::class.java)
                startActivity(intent)
                return true
            }

        }


        return super.onOptionsItemSelected(item)
    }

    //funzione per il cambio di fragment dopo i click su bottom navigation bar

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}