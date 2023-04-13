package it.omarkiarafederico.skitracker.view.skimap

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import it.omarkiarafederico.skitracker.R
import it.omarkiarafederico.skitracker.databinding.ActivityMapBinding
import it.omarkiarafederico.skitracker.view.tutorial.WelcomeActivity
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay


class MapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapBinding //da ritoccare il gradle su buildFeatures

    override fun onCreate(savedInstanceState: Bundle?) {
        // creazione activity
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // inizializzazione mappa
        val map : MapView = findViewById(R.id.map)
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)
        map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
        map.isTilesScaledToDpi = true
        Configuration.getInstance().userAgentValue = "skitracker"

        // aggiungo la possibilità di poter ruotare la mappa con due dita
        val mRotationGestureOverlay = RotationGestureOverlay(map)
        mRotationGestureOverlay.setEnabled(true)
        map.getOverlays().add(mRotationGestureOverlay)

        // aggiungo la barra della scala della dimensione in km reali nella mappa
        val scaleBarOverlay = ScaleBarOverlay(map)
        scaleBarOverlay.setCentred(true)
        scaleBarOverlay.setScaleBarOffset(200, 10)
        map.overlays.add(scaleBarOverlay)

        // creo un controller della mappa per impostare una posizione iniziale
        val mapController = map.controller
        val startPoint = GeoPoint(46.370066950988, 10.659417137504)
        mapController.setCenter(startPoint)
        mapController.animateTo(startPoint, 16.0, 1200)

        // ottengo la posizione precisa dell'utente (tramite il gps)
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    Log.i("SkiTracker GPS Location", "Fine Location Allowed")
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    Log.i("SkiTracker GPS Location", "Coarse Location Allowed")
                } else -> {
                    Log.w("SkiTracker GPS Location", "User denied GPS access authorization")
                }
            }
        }

        Log.i("SkiTracker GPS Location", "Trying to get GPS location.")
        locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("SkiTracker GPS Location", "Error - not authorized to use GPS location.")
        }

        fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener {loc: Location -> drawMarkerToMap(loc, map)}

        //configurazione bottom navigation bar
        binding.bottomNavigationView.setOnItemReselectedListener {
            when(it.itemId) {
            //    R.id.cronologia -> replaceFragment(CronologiaFragment())
            //    R.id.infopiste -> replaceFragment(InfoPisteFragment())
            //    R.id.map -> replaceFragment(this)
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
        fragmentTransaction.replace(R.id.map, fragment)  //DA VERIFICARE...
        fragmentTransaction.commit()
    }

    // funzione che prende una Location e la va a rappresentare graficamente nella mappa con un
    // apposito marker
    fun drawMarkerToMap(loc: Location, map: MapView) {
        // questo è il punto preciso della posizione rilevata
        val gpsPoint = GeoPoint(loc.latitude, loc.longitude)

        // creo un marker che mostra la posizione del gps sulla mappa
        val gpsPointMarker = Marker(map)
        gpsPointMarker.position = gpsPoint
        gpsPointMarker.title = "Posizione corrente"

        // metto un'icona personalizzata nel marker
        val markerIcon: Drawable? = ResourcesCompat.getDrawable(getResources(),
            R.drawable.gpsmarker, null)
        gpsPointMarker.icon = markerIcon

        // aggiungo il marker alla mappa
        map.overlays.add(gpsPointMarker)
        Log.i("SkiTracker GPS Location", "GPS Location Marker added at: " +
                "${loc.latitude} - ${loc.longitude}")
    }
}