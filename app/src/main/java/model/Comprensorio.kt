package model

import org.json.JSONObject
import roomdb.Comprensorio
import utility.ApiCallThread

class Comprensorio(private var id: Int, name: String) {
    private var nome:String = name

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

    fun popolateWithOnlineData() {
        // ottengo il json dei dati del comprensorio
        val skiAreaJsonString = ApiCallThread().main("https://skimap.org/SkiAreas/view/${this.id}.json")
        val skiAreaJsonObj = JSONObject(skiAreaJsonString)

        // vado a popolare il comprensorio con i dati appena ricevuti
        this.popolateWithJson(skiAreaJsonObj)
    }

    private fun popolateWithJson(obj: JSONObject) {
        try {
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
        } catch (e: org.json.JSONException) {
            // se manncano dei dati nel JSON ricevuto, metto questo nome particolare per segnalarlo
            // all'activity principale
            this.nome = "NO"
        }
    }

    fun getId(): Int {
        return this.id
    }

    fun getNome(): String {
        return this.nome
    }

    fun isOperativo(): Boolean {
        return this.aperto
    }

    fun convertToEntityClass(): Comprensorio {
        return Comprensorio(this.id, this.nome, this.aperto, this.numPiste, this.numImpianti, this.website,
                            this.presenteSnowpark, this.presentiPisteNotturne, this.latitudine, this.longitudine,
                            this.maxAltitudine, this.minAltitudine)
    }
}