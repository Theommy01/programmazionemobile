package it.omarkiarafederico.skitracker.view.skimap

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import it.omarkiarafederico.skitracker.R
import it.omarkiarafederico.skitracker.databinding.ActivityMapBinding
import it.omarkiarafederico.skitracker.view.selezionecomprensorio.SelezioneComprensorio
import it.omarkiarafederico.skitracker.view.tutorial.WelcomeActivity
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay
import roomdb.LocalDB


class MapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapBinding //da ritoccare il gradle su buildFeatures

    override fun onCreate(savedInstanceState: Bundle?) {
        // creazione activity
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // controllo se l'utente ha già visto il tutorial e/o ha già selezionato il comprensorio
        // se non ha fatto almeno una delle due cose, lo redirigo alle varie activities
        // TODO - FORSE METTEREI STA ROBA IN UN ViewModel
        val db = Room.databaseBuilder(this.applicationContext, LocalDB::class.java, "LocalDatabase")
            .allowMainThreadQueries().build()
        var intent: Intent? = null
        // controllo se il tutorial è stato completato e se il comprensorio è stato scelto
        if (db.localDatabaseDao().isTutorialCompletato() != 1)
            intent = Intent(this.applicationContext, WelcomeActivity::class.java)
        else if (db.localDatabaseDao().getIdComprensorio() == null)
            intent = Intent(this.applicationContext, SelezioneComprensorio::class.java)
        // se necessario, apro la activity che serve
        if (intent != null) {
            // svuoto il back stack per evitare bug
            finishAffinity()
            // avvio la activity che serve
            startActivity(intent)
        }

        // inizializzazione mappa
        val map : MapView = findViewById(R.id.map)
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)
        map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
        map.isTilesScaledToDpi = true
        Configuration.getInstance().userAgentValue = "skitracker"

        // aggiungo la possibilità di poter ruotare la mappa con due dita
        val mRotationGestureOverlay = RotationGestureOverlay(map)
        mRotationGestureOverlay.isEnabled = true
        map.overlays.add(mRotationGestureOverlay)

        // aggiungo la barra della scala della dimensione in km reali nella mappa
        val scaleBarOverlay = ScaleBarOverlay(map)
        scaleBarOverlay.setCentred(true)
        scaleBarOverlay.setScaleBarOffset(200, 10)
        map.overlays.add(scaleBarOverlay)

        // creo un controller della mappa per impostare una posizione iniziale
        // TODO - QUI ci andrà la posizione del comprensorio selezionato!!!
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

    // creazione menu a tendina
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.optionsmenu, menu)
        return true
    }

    // configurazione funzioni del menù a tendina
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) { //nel when, si ricorda che si possono mettere solo le costanti

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
        val markerIcon: Drawable? = ResourcesCompat.getDrawable(
            resources,
            R.drawable.gpsmarker, null)
        gpsPointMarker.icon = markerIcon

        // aggiungo il marker alla mappa
        map.overlays.add(gpsPointMarker)
        Log.i("SkiTracker GPS Location", "GPS Location Marker added at: " +
                "${loc.latitude} - ${loc.longitude}")

        // personalizzo il marker, da valutare

        /*

        val marker = Marker(map)
        marker.position = GeoPoint(loc.latitude, loc.longitude)
        marker.icon = resources.getDrawable(R.drawable.marker_icon) // Replace with your own icon
        map.overlays.add(marker)

         */
    }

    /*

    //Funzione che permette di visualizzare i dettagli di un posto selezionato
    fun onSingleTap(event: MotionEvent?): Boolean {
        val map : MapView = findViewById(R.id.map)

        val projection = map.projection
            val location = projection.fromPixels(event!!.x.toInt(), event.y.toInt())
            val geoPoint = GeoPoint(location.latitude, location.longitude)
            val address = getAddress(geoPoint)
            Toast.makeText(this, address, Toast.LENGTH_LONG).show()
            return true
        }

     */
/*
    fun getAddress(geoPoint: GeoPoint): String {
        val geocoder = Geocoder(this)
        val addresses = geocoder.getFromLocation(geoPoint.latitude, geoPoint.longitude, 1)
        if (addresses != null) {
           // return addresses.get(0).getAddressLine(0)
            return addresses[0].getAddressLine(0)
        }


    }

 */

    //Funzione per personalizzare i pop-up della scelta di una specifica località

    /*
    fun showPopup(view: View) {

        val popup = PopupMenu(this, view)
        popup.inflate(R.menu.header_menu)
        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->
            when (item!!.itemId) {
                R.id.header1 -> {
                    // TODO: Add your code here
                    true
                }
                R.id.header2 -> {
                    // TODO: Add your code here
                    true
                }
                R.id.header3 -> {
                    // TODO: Add your code here
                    true
                }
                else -> false
            }
        })
        popup.show()
    }

     */

}