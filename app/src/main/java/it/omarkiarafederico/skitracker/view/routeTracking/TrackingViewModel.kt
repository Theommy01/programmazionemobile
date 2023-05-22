package it.omarkiarafederico.skitracker.view.routeTracking

import androidx.lifecycle.ViewModel
import model.Comprensorio
import model.Pista

class TrackingViewModel : ViewModel() {
    private lateinit var mySkiArea: Comprensorio
    private lateinit var myPista: Pista

    fun setComprensorio(comp: Comprensorio) {
        this.mySkiArea = comp
    }

    fun setPista(pista: Pista) {
        this.myPista = pista
    }

    fun getComprensorio(): Comprensorio {
        return this.mySkiArea
    }

    fun getPista(): Pista {
        return this.myPista
    }
}