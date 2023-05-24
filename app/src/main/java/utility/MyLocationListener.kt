package utility

import android.location.Location
import android.location.LocationListener
import android.os.Handler
import android.os.Looper
import it.omarkiarafederico.skitracker.view.routeTracking.TrackingViewModel
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

class MyLocationListener(private val mapView: MapView, private val viewModel: TrackingViewModel) : LocationListener {
    private var startPointLocation: Location? = null

    override fun onLocationChanged(location: Location) {
        // Ottieni le nuove coordinate della posizione
        val newLatitude: Double = location.latitude
        val newLongitude: Double = location.longitude

        // Crea un oggetto GeoPoint con le nuove coordinate
        val newPoint = GeoPoint(newLatitude, newLongitude)
        if (this.startPointLocation == null)
            this.startPointLocation = location

        // vado ad impostare i valori istantanei di velocità e distanza sul viewModel
        viewModel.updateSpeed(location.speed)
        viewModel.updateDistance(location.distanceTo(this.startPointLocation!!))
        viewModel.updateAltitudes(location.altitude)

        // Aggiorna la posizione della mappa sulla thread principale
        Handler(Looper.getMainLooper()).post {
            mapView.controller.animateTo(newPoint)
        }
    }
}