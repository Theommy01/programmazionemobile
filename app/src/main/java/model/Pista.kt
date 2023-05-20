package model

import roomdb.Pista
import java.io.Serializable

class Pista(private var id: Int, private var nome: String, private var idComprensorio: Int): Serializable {
    private lateinit var difficolta: String

    constructor(pistaFromDb: Pista): this(pistaFromDb.id, pistaFromDb.nome, pistaFromDb.idComprensorio) {
        when(pistaFromDb.difficolta) {
            "easy" -> this.difficolta = "Facile"
            "intermediate" -> this.difficolta = "Medio"
            "advanced" -> this.difficolta = "Avanzato"
        }
    }

    fun getNome(): String {
        return this.nome
    }

    fun getDifficolta(): String {
        return this.difficolta
    }
}