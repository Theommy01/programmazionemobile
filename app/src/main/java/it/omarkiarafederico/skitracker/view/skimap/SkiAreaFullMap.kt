package it.omarkiarafederico.skitracker.view.skimap

import org.json.JSONObject
import org.osmdroid.bonuspack.kml.KmlDocument
import utility.ApiCallThread

class SkiAreaFullMap {
    fun ottieniXmlMappaComprensorio(lat: Double, long: Double, zoomLevel: Int): KmlDocument {
        // vado a ottenere il JSON che contiene le informazioni geografiche del comprensorio
        // in particolar modo, mi serviranno sapere le coordinate in cui la sua area è compresa
        val skiAreaGeocodedText = ApiCallThread()
            .main("https://nominatim.openstreetmap.org/reverse?lat=${lat}&lon=${long}&format=geojson&extratags=1&zoom=${zoomLevel}")
        val skiAreaGeocodedJson = JSONObject(skiAreaGeocodedText)

        // ottengo le coordinate dell'area del comprensorio
        val skiAreaFeatures = skiAreaGeocodedJson.getJSONArray("features")[0] as JSONObject
        val skiAreaBbox = skiAreaFeatures.getJSONArray("bbox")
        val skiAreaW = skiAreaBbox[0]
        val skiAreaS = skiAreaBbox[1]
        val skiAreaE = skiAreaBbox[2]
        val skiAreaN = skiAreaBbox[3]

        // questo è l'XML della richiesta da fare a Overpass per ottenere la mappa completa del comprensorio,
        // comprendente le piste, che verrà ritornata in formato OpenStreetMap XML.
        val requestBody = "" +
                "<osm-script output=\"xml\" timeout=\"25\">" +
                "   <union>" +
                "       <query type=\"way\">" +
                "           <has-kv k=\"piste:type\" />" +
                "           <bbox-query w=\"$skiAreaW\" s=\"$skiAreaS\" e=\"$skiAreaE\" n=\"$skiAreaN\" />" +
                "       </query>" +
                "   </union>" +
                "   <print mode=\"body\" />" +
                "   <recurse type=\"down\" />" +
                "   <print mode=\"skeleton\" order=\"quadtile\" />" +
                "</osm-script>"

        val skiAreaOsmXml = ApiCallThread().callWithXmlArgument(
            requestBody,
            "https://overpass-api.de/api/interpreter"
        )

        // converto l'osm xml in un geojson
        val kmlDocument = KmlDocument()
        kmlDocument.parseGeoJSON(skiAreaOsmXml)

        // ritorno il geojson
        return kmlDocument
    }
}