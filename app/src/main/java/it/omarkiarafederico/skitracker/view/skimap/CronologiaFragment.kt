package it.omarkiarafederico.skitracker.view.skimap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider
import it.omarkiarafederico.skitracker.R
import roomdb.RoomHelper

class CronologiaFragment : Fragment() {
    private var myViewModel: CronologiaViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // ottengo il riferimento al viewmodel
        myViewModel = activity?.let {ViewModelProvider(it)[CronologiaViewModel::class.java]}

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cronologia, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // metodo padre
        super.onViewCreated(view, savedInstanceState)

        // ottengo la lista dei comprensori disponibili
        val dbCon = RoomHelper().getDatabaseObject(requireContext())
        val skiAreasFromDb = dbCon.localDatabaseDao().getSkiAreasList()
        this.myViewModel?.setListaComprensori(skiAreasFromDb)

        // vado ad inserire i comprensori all'interno dello Spinner
        val spinner = view.findViewById<Spinner>(R.id.skiAreaSelectionSpinner)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, this.myViewModel!!.getListaComprensori())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }
}