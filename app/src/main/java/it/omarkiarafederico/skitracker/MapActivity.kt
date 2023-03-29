package it.omarkiarafederico.skitracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MinimapOverlay
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay


class MapActivity : AppCompatActivity() {
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
        val startPoint = GeoPoint(46.370066950988, 10.659417137504)
        mapController.setCenter(startPoint)
        mapController.animateTo(startPoint, 16.0, 1200)

        // aggiungo la possibilità di poter ruotare la mappa con due dita
        val mRotationGestureOverlay = RotationGestureOverlay(map)
        mRotationGestureOverlay.setEnabled(true)
        map.getOverlays().add(mRotationGestureOverlay)

        //aggiungo Map Scale bar overlay (PERCHE NON FUNZIONA IL CONVERTITORE JAVA KOTLIN CANE DELLE BESTIE|)
        final Context context = this.getActivity();
        final DisplayMetrics dm = context.getResources().getDisplayMetrics();
        mScaleBarOverlay = new ScaleBarOverlay(mMapView);
        mScaleBarOverlay.setCentred(true);
//play around with these values to get the location on screen in the right place for your application
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
        mMapView.getOverlays().add(this.mScaleBarOverlay);

        //aggiungo la minimappa integrata
        val mMinimapOverlay = MinimapOverlay(this, map.getTileRequestCompleteHandler())
        mMinimapOverlay.setWidth(dm.widthPixels / 5)
        mMinimapOverlay.setHeight(dm.heightPixels / 5)
        //optionally, you can set the minimap to a different tile source
        //mMinimapOverlay.setTileSource(....);
        //optionally, you can set the minimap to a different tile source
        //mMinimapOverlay.setTileSource(....);
        map.getOverlays().add(mMinimapOverlay)
    }
}