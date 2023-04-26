package it.omarkiarafederico.skitracker.view.skimap

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import it.omarkiarafederico.skitracker.R
import model.Comprensorio
import org.osmdroid.bonuspack.kml.KmlDocument
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
    private lateinit var mySkiArea: Comprensorio
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        val myActivity = this.activity as MapActivity
        mySkiArea = myActivity.getComprensorioSelezionato()

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
        Log.e("sdfjhkgsaioòlduagko", "sdgfldk: ${mySkiArea.getLongitudine()}")
        renderKMLskiArea(mySkiArea.getLatitudine(), mySkiArea.getLongitudine(),
            mySkiArea.getZoomLevel())

        val mapController = map?.controller
        val startPoint = GeoPoint(mySkiArea.getLatitudine(), mySkiArea.getLongitudine())
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

        // uso gli snackbar per chiedere all'utente se si vede tutto nella mappa o bisogna regolare
        // lo zoom
        val snackbar = Snackbar.make(view, "Problemi con la mappa?", Snackbar.LENGTH_LONG)
        snackbar.setAction("Regola zoom") {

        }
        snackbar.show()
    }

    // ottiene l'istanza della mappa di OSM presente in questo fragment.
    private fun getMap(): MapView? {
        return view?.findViewById(R.id.map)
    }

    // quando viene caricata l'activity completamente, viene richiesta la geolocalizzazione.
    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getCurrentLocation()
    }

    // vado ad ottenere le coordinate precise della mia posizione (tramite GPS); una volta ottenute,
    // vado a creare un elemento grafico (overlay) che rappresenta la mia posizione sulla mappa.
    private fun getCurrentLocation() {
        val map = getMap()
        val locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), map)
        locationOverlay.enableMyLocation()

        map?.overlays?.add(locationOverlay)
    }

    // la funzione prende le coordinate del comprensorio e il livello di zoom per ottenere il KML
    // document che contiene i punti del comprensorio e rappresentarlo sulla mappa
    private fun renderKMLskiArea(lat: Double, long: Double, zoomLevel: Int) {
        val skiAreaKml: KmlDocument = SkiAreaFullMap().ottieniXmlMappaComprensorio(lat,
            long, zoomLevel)

        val map = getMap()
        map?.overlays?.add(skiAreaKml.mKmlRoot.buildOverlay(map, null, null, skiAreaKml))
        map?.invalidate()
    }
}
