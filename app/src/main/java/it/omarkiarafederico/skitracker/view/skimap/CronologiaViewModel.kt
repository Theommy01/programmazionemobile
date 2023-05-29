package it.omarkiarafederico.skitracker.view.skimap

import android.util.Log
import androidx.lifecycle.ViewModel
import model.Comprensorio

class CronologiaViewModel: ViewModel() {
    private val listaComprensori = ArrayList<Comprensorio>()

    fun setListaComprensori(listaFromDb: List<roomdb.Comprensorio>) {
        for (skiArea in listaFromDb)
            this.listaComprensori.add(Comprensorio(skiArea))
    }

    fun getListaComprensori(): ArrayList<Comprensorio> {
        Log.e("La cacca fraida", "Pisellone: ${this.listaComprensori[1].toString()}")
        return this.listaComprensori
    }
}