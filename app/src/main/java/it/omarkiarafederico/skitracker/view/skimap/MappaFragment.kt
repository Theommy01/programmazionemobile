package it.omarkiarafederico.skitracker.view.skimap

import android.content.Context
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import it.omarkiarafederico.skitracker.R
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


class MappaFragment : Fragment() {
    private lateinit var locationManager: LocationManager
    private var lastLocation: Location? = null
    private var distance = 0f
    private var speed = 0f
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        return inflater.inflate(R.layout.fragment_mappa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // inizializzazione mappa
        val map = getMap()
        if (map != null) {
            map.setTileSource(TileSourceFactory.MAPNIK)
            map.setMultiTouchControls(true)
            map.zoomController?.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
            map.isTilesScaledToDpi = true
        }
        Configuration.getInstance().userAgentValue = "skitracker"

        // aggiungo la possibilità di poter ruotare la mappa con due dita
        val mRotationGestureOverlay = RotationGestureOverlay(map)
        mRotationGestureOverlay.isEnabled = true
        if (map != null) {
            map.overlays?.add(mRotationGestureOverlay)
        }

        // aggiungo la barra della scala della dimensione in km reali nella mappa.
        val scaleBarOverlay = ScaleBarOverlay(map)
        scaleBarOverlay.setCentred(true)
        scaleBarOverlay.setScaleBarOffset(200, 10)
        map?.overlays?.add(scaleBarOverlay)

        // creo un controller della mappa per impostare una posizione iniziale
        // TODO - QUI ci andrà la posizione del comprensorio selezionato!!!
        val mapKml = SkiAreaFullMap().ottieniXmlMappaComprensorio(46.370066950988,
            10.659417137504, 16)
        map?.overlays?.add(mapKml.mKmlRoot.buildOverlay(map, null, null, mapKml))
        map?.invalidate()


        val mapController = map?.controller
        val startPoint = GeoPoint(46.370066950988, 10.659417137504)
        mapController?.setCenter(startPoint)
        mapController?.animateTo(startPoint, 16.0, 1200)

        // gestione del FAB per la geolocalizzazione manuale
        val fab = view.findViewById<FloatingActionButton>(R.id.getPositionFAB)
        fab.setOnClickListener {
            // Azione da eseguire quando l'utente preme il pulsante
            val locationOverlay = map?.overlays?.get(map.overlays.lastIndex) as MyLocationNewOverlay

            if (locationOverlay.myLocation == null) {
                Toast.makeText(this.context, "Ottenimento della posizione tramite GPS...",
                    Toast.LENGTH_SHORT).show()
                getCurrentLocation()
            }
            else
                mapController?.setCenter(locationOverlay.myLocation)
        }

        // tracciamento di una linea che congiunge dei punti. Per ora, esempio con due

        val geoPoints = ArrayList<GeoPoint>()
        // aggiungo i punti che voglio disegnare
        geoPoints.add(GeoPoint(45.1234,7.5678))
        geoPoints.add(GeoPoint(46.370066950988, 10.659417137504))

        //inserisco la linea
        val line = Polyline()
        line.setPoints(geoPoints)
        //opzioni personalizzabili
        line.width = 5f
        line.color = Color.BLUE
        line.setGeodesic(true)

        //inserimento effettivo
        map?.overlays?.add(line)

        // tracciamento continuo dell'utilizzatore
        val myLocationOverlay = MyLocationNewOverlay(map)
        map?.overlays?.add(myLocationOverlay)
        myLocationOverlay.enableMyLocation()

        // raccolta dati statistici

        /*

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0F, this) //accesso al GPS
        var newLocation =
        onLocationChanged(newLocation)

         */

        /*

      val start = GeoPoint(48.8583, 2.2945)
      val end = GeoPoint(51.5074, 0.1278


      val distanceInMeters = start.distanceToAsDouble(end)
      print(distanceInMeters)

       */

    }

    // funzione che consente di accede alla mappa anche da altre classi
    private fun getMap(): MapView? {
        return view?.findViewById(R.id.map)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getCurrentLocation()
    }

    private fun getCurrentLocation() {
        val map = getMap()
        val locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), map)
        locationOverlay.enableMyLocation()

        map?.overlays?.add(locationOverlay)
    }

    //calcolo dati statistici
    private fun onLocationChanged(location: Location){
        if (lastLocation != null) {
            distance += location.distanceTo(lastLocation!!)
            speed = distance/ (location.time - lastLocation!!.time)
        }
        lastLocation = location
    }
}
