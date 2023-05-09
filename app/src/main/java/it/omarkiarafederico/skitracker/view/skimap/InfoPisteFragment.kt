package it.omarkiarafederico.skitracker.view.skimap

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import it.omarkiarafederico.skitracker.R
import java.util.regex.Pattern

class InfoPisteFragment : Fragment() {
    private lateinit var skiArea: model.Comprensorio

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        val myActivity = this.activity as MapActivity
        skiArea = myActivity.getComprensorioSelezionato()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_piste, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titolo: TextView = view.findViewById(R.id.titolo)
        val numPiste: TextView = view.findViewById(R.id.numPiste)
        val impiantiRisalita: TextView = view.findViewById(R.id.impiantiRisalita)
        val max: TextView = view.findViewById(R.id.altMax)
        val min: TextView = view.findViewById(R.id.altMin)
        val sito: TextView = view.findViewById(R.id.website)

        val url = skiArea.getWebSite()
        val text = "sito internet"

        titolo.text = skiArea.getNome() //modifica di prova
        numPiste.text = "${skiArea.getNumPiste()}"
        impiantiRisalita.text = "${skiArea.getNumImpianti()}"
        max.text = "${skiArea.getMaxAlt()}"
        min.text = "${skiArea.getMinAlt()}"

        sito.text = text

        //aggiungo link al sito
        sito.movementMethod = LinkMovementMethod.getInstance()
        Linkify.addLinks(sito, Pattern.compile(url), url)

        /* metodo alternativo
        sito.setOnClickListener {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
        }
         */

        //Personalizzo la scritta che indica se quel comprensorio è aperto o chiuso
        val stato = view.findViewById<TextView>(R.id.stato)
        val aperto = skiArea.isOperativo()

        if (aperto) {
            stato.text = "APERTO"
            context?.let { ContextCompat.getColor(it, R.color.green) }
                ?.let { stato.setBackgroundColor(it) }
        } else {
            stato.text = "CHIUSO"
            context?.let { ContextCompat.getColor(it, R.color.red) }
                ?.let { stato.setBackgroundColor(it) }
        }

        //gestisco le scritte snowpark e piste notturne

        val snowpark = view.findViewById<TextView>(R.id.snowpark)
        val night = view.findViewById<TextView>(R.id.piste_notturne)

        val showsnow = skiArea.getSnowPark()
        val shownight = skiArea.getNight()

        if (showsnow){
            snowpark.visibility = View.VISIBLE
        } else {
            snowpark.visibility = View.GONE
        }

        if (shownight){
            night.visibility = View.VISIBLE
        } else {
            night.visibility = View.GONE
        }

        //ELENCO PISTE DISPONIBILI

       /* val listView = view.findViewById<ListView>(R.id.elenco_piste)
        val elenco= mutableListOf("primo","secondo","terzo")
        elenco.add("quarto")

        val arrayAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, elenco)

        listView.adapter = arrayAdapter

        */

    }


}