package it.omarkiarafederico.skitracker.view.routeTracking

import androidx.lifecycle.ViewModel
import model.Pista

class TrackingFragmentViewModel : ViewModel() {
    private lateinit var myPista: Pista

    fun setPista(pista: Pista) {
        this.myPista = pista
    }

    fun getPista(): Pista {
        return this.myPista
    }
}