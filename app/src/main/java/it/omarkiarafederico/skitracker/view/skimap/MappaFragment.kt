package it.omarkiarafederico.skitracker.view.skimap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import it.omarkiarafederico.skitracker.R
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


class MappaFragment : Fragment() {
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
}
