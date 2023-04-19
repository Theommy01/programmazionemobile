package it.omarkiarafederico.skitracker.view.skimap

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.room.Room
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import it.omarkiarafederico.skitracker.R
import it.omarkiarafederico.skitracker.databinding.ActivityMapBinding
import it.omarkiarafederico.skitracker.view.tutorial.WelcomeActivity
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
            //intent = Intent(this.applicationContext, SelezioneComprensorio::class.java)
        // se necessario, apro la activity che serve
        if (intent != null) {
            // svuoto il back stack per evitare bug
            finishAffinity()
            // avvio la activity che serve
            startActivity(intent)
        }



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
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("SkiTracker GPS Location", "Error - not authorized to use GPS location.")
        }
        Log.i("SkiTracker GPS Location", "Trying to get GPS location.")
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))
        val fm: FragmentManager = supportFragmentManager
       // val mapFragment = fm.findFragmentById(R.id.mappaFragment)
        val mapFragment = MappaFragment()
     //   val map: MapView = findViewById(R.id.map)

        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener {loc: Location ->
                mapFragment.getMap()?.let {
                    mapFragment.drawMarkerToMap(loc, it)
                }
            }
        //configurazione bottom navigation bar

        replaceFragment(MappaFragment()) //per inserire MappaFragment come quella che si apre di default

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                    R.id.cronologiaFragment -> replaceFragment(CronologiaFragment())
                    R.id.infopisteFragment -> replaceFragment(InfoPisteFragment())
                    R.id.mappaFragment -> replaceFragment(MappaFragment())
                else -> {

                }

            }
            true
        }
    }



    // creazione menu a tendina
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.map_view_menu, menu)
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
     fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)  //DA VERIFICARE...
        fragmentTransaction.commit()
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