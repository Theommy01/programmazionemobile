package it.omarkiarafederico.skitracker.view.skimap

import android.app.AlertDialog
import android.icu.text.CaseMap.Title
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import it.omarkiarafederico.skitracker.R

class InfoPisteFragment : Fragment() {

    private lateinit var dialog: AlertDialog
    private lateinit var edit: EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_piste, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titolo: TextView = view.findViewById(R.id.titolo)
        titolo.text = "MODIFICA" //modifica di prova
        // val numPiste
        // val impianti risalita
        // val altitudinemin
        // val altitudinemax
        // val sito

        /*
        var comprensorio = INSERIRE QUI IL NOME DEL COMPRENSORIO
        titolo.text = "$comprensorio"

         */

        //QUI CI ANDRANNO INSERITI TUTTI I DETTAGLI. PRENDEREMO I CODICI DI OGNI SINGOLA
        //TEXTVIEW E NE MODIFICHEREMO I CONTENUTI
    }

}