package model

class Comprensorio {
    private var id:Int = 0

    private var nome:String = ""
    private var aperto:Boolean = false
    private var website:String = ""

    private var numPiste:Int = 0
    private var numImpianti:Int = 0

    private var presenteSnowpark:Boolean = false
    private var presentiPisteNotturne:Boolean = false

    private var latitudine: Double = 0.0
    private var longitudine: Double = 0.0

    private var minAltitudine: Int = 0
    private var maxAltitudine: Int = 0

    constructor(id:Int, nome:String) {
        this.id = id
        this.nome = nome
    }

    fun completeInit(nome:String, aperto:Boolean, website:String, numPiste: Int, numImpianti:Int,
        snowpark:Boolean, pisteNotturne:Boolean, lat:Double, long:Double, minAlt: Int, maxAlt:Int) {
        this.nome = nome
        this.aperto = aperto
        this.website = website
        this.numPiste = numPiste
        this.numImpianti = numImpianti
        this.presenteSnowpark = snowpark
        this.presentiPisteNotturne = pisteNotturne
        this.latitudine = lat
        this.longitudine = long
        this.minAltitudine = minAlt
        this.maxAltitudine = maxAlt
    }

    fun getId(): Int {
        return this.id
    }

    fun getNome(): String {
        return this.nome
    }
}