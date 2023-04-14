package it.omarkiarafederico.skitracker.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import it.omarkiarafederico.skitracker.R
import model.Comprensorio
import okhttp3.OkHttpClient
import okhttp3.Request
import org.w3c.dom.Document
import org.xml.sax.InputSource
import java.io.StringReader
import java.lang.NullPointerException
import javax.xml.parsers.DocumentBuilderFactory

class SelezioneComprensorio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selezione_comprensorio)
        supportActionBar?.setSubtitle("Caricamento comprensori...")

        // questa è la lista di comprensori
        var listaComprensori: Array<Comprensorio> = emptyArray()

        // ottengo la lista dei comprensori
        // compongo la richiesta API
        val client = OkHttpClient()
        val request = Request.Builder().url("https://skimap.org/Regions/view/86.xml").build()

        // creo il builder che creerà l'oggetto xml che conterrà la lista dei comprensori d'italia
        val builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        lateinit var xmlDoc: Document

        // creo il thread che va ad eseguire la richiesta (su android non è possibile generare
        // richieste di rete sul thread principale)
        val handler = Handler(Looper.getMainLooper())
        Thread {
            // eseguo la chiamata API
            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    val xmlString = response.body!!.string()
                    xmlDoc = builder.parse(InputSource(StringReader(xmlString)))
                    Log.i("SkiTracker API Call", "{SkiMap} Got all ski areas.")
                } else {
                    Log.e("SkiTracker API Call", "{SkiMap} Unable to get ski areas list.")
                }

                handler.post {
                    // qui il thread è terminato, ritorno a quello principale e vado a trattare i
                    // dati ricevuti dall'API
                    val skiAreasNode = xmlDoc.getElementsByTagName("skiAreas").item(0)
                    val skiAreaNodeList = skiAreasNode.childNodes
                    // vado a popolare l'array di comprensori
                    for (i in 1 until skiAreaNodeList.length) {
                        try {
                            val skiAreaNode = skiAreaNodeList.item(i)
                            val skiAreaNodeText = skiAreaNode.textContent
                            val skiAreaNodeId = skiAreaNode.attributes.item(0).nodeValue.toInt()

                            val comp = Comprensorio(skiAreaNodeId, skiAreaNodeText)
                            listaComprensori += comp
                        } catch (_: NullPointerException) {}
                    }
                    // visualizzo l'array di comprensori
                    supportActionBar?.subtitle = "${listaComprensori.size} comprensori disponibili (paese: IT)"

                    val gianfranco = this.findViewById<TextView>(R.id.textView)
                    var luigi = "OK:"
                    for (item:Comprensorio in listaComprensori) {
                        luigi += "ID: ${item.getId()} - Nome: ${item.getNome()}  |||   "
                    }
                    gianfranco.text = luigi
                }
            }
        }.start()
    }
}