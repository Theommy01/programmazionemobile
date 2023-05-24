package it.omarkiarafederico.skitracker.view.routeTracking

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import model.Comprensorio
import model.Pista
import java.time.LocalDateTime

class TrackingViewModel : ViewModel() {
    private lateinit var mySkiArea: Comprensorio
    private lateinit var myPista: Pista

    private val speedArray = ArrayList<Float>()
    private val altitudesArray = ArrayList<Double>()
    val startingDateTime = LocalDateTime.now()

    var instantSpeed = MutableLiveData(0.0F)
    val totalDistance = MutableLiveData(0.0F)

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

    fun updateSpeed(speed: Float) {
        this.instantSpeed.value = speed
        this.speedArray.add(speed)
    }

    fun updateDistance(distance: Float) {
        this.totalDistance.value = this.totalDistance.value?.plus(distance)
    }

    fun updateAltitudes(altitude: Double) {
        this.altitudesArray.add(altitude)
    }
}