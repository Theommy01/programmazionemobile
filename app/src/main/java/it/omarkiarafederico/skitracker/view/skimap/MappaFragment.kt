package it.omarkiarafederico.skitracker.view.skimap

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import it.omarkiarafederico.skitracker.R
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay


class MappaFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        val view = inflater.inflate(R.layout.fragment_mappa, container, false)

        // inizializzazione mappa
        val map: MapView = view.findViewById(R.id.map)
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
        return view
    }






    // funzione che prende una Location e la va a rappresentare graficamente nella mappa con un
    // apposito marker
    public fun drawMarkerToMap(loc: Location, map: MapView) {
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
}
