package it.omarkiarafederico.skitracker.view.selezionecomprensorio

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import model.Comprensorio
import org.w3c.dom.Document
import org.xml.sax.InputSource
import utility.ApiCallThread
import java.io.StringReader
import java.lang.NullPointerException
import javax.xml.parsers.DocumentBuilderFactory

class SkiAreaListDownloadThread {
    // la suspend fun serve per eseguire questa funzione in un thread separato, senza che vado
    // a bloccare il thread principale (quello dell'activity) lasciando quindi vedere solo una
    // schermata bianca
    suspend fun getSkiAreaList(): ArrayList<Comprensorio> {
        delay(50)
        // questa è la lista di comprensori
        val listaComprensori:ArrayList<Comprensorio> = arrayListOf()

        // creo il builder che creerà l'oggetto xml che conterrà la lista dei comprensori d'italia
        val builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        lateinit var skiAreaListXml: Document

        // vado a fare la richiesta api per poter ottenere la lista completa dei comprensori
        val skiAreaListString = ApiCallThread().main("https://skimap.org/Regions/view/86.xml")

        // ho ottenuto la lista completa dei comprensori, ora devo vedere se aggiungerli o no
        skiAreaListXml = withContext(Dispatchers.IO) {
            builder.parse(InputSource(StringReader(skiAreaListString)))
        }
        val skiAreasNode = skiAreaListXml.getElementsByTagName("skiAreas").item(0)
        val skiAreaNodeList = skiAreasNode.childNodes
        for (i in 1 until skiAreaNodeList.length) {
            try {
                val skiAreaNode = skiAreaNodeList.item(i)
                val skiAreaNodeId = skiAreaNode.attributes.item(0).nodeValue.toInt()
                val skiAreaName = skiAreaNode.textContent

                val comp = Comprensorio(skiAreaNodeId, skiAreaName)
                listaComprensori.add(comp)
            } catch (_: NullPointerException) {}
        }

        return listaComprensori
    }
}