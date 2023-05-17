package model

import roomdb.Pista

class Pista(private var id: Int, nome: String, idComprensorio: Int) {
    private lateinit var difficolta: String

    constructor(pistaFromDb: Pista): this(pistaFromDb.id, pistaFromDb.nome, pistaFromDb.idComprensorio) {
        when(pistaFromDb.difficolta) {
            "easy" -> this.difficolta = "Facile"
            "intermediate" -> this.difficolta = "Medio"
            "advanced" -> this.difficolta = "Avanzato"
        }
    }
}