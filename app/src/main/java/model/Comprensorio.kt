package model

import org.json.JSONObject

class Comprensorio(private var id: Int, private var name: String) {

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

    fun popolateWithJson(obj: JSONObject) {
        this.nome = obj.getString("name")
        this.aperto = obj.getString("operating_status") == "Operating"
        this.website = obj.getString("official_website")
        this.numPiste = obj.getInt("run_count")
        this.numImpianti = obj.getInt("lift_count")
        this.presenteSnowpark = obj.getString("terrain_park") == "Yes"
        this.presentiPisteNotturne = obj.getString("night_skiing") == "Yes"
        this.latitudine = obj.getDouble("latitude")
        this.longitudine = obj.getDouble("longitude")
        this.minAltitudine = obj.getInt("top_elevation")
        this.maxAltitudine = obj.getInt("bottom_elevation")
    }

    fun getId(): Int {
        return this.id
    }

    fun getNome(): String {
        return this.nome
    }
}