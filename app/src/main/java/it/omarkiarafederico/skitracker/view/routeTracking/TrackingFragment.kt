package it.omarkiarafederico.skitracker.view.routeTracking

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import it.omarkiarafederico.skitracker.R
import model.Pista

class TrackingFragment : Fragment() {
    private var myViewModel: TrackingViewModel? = null
    private lateinit var selectedPista: Pista

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // ottengo il riferimento al viewmodel
        myViewModel = activity?.let {
            ViewModelProvider(it)[TrackingViewModel::class.java]
        }

        // preparo l'activity: Metto un titolo diverso e disabilito il tasto Back nella titleBar
        val myActivity = this.activity as RouteTrackingActivity
        myActivity.supportActionBar?.title = "Tracciamento percorso"
        myActivity.supportActionBar?.subtitle = ""
        myActivity.supportActionBar?.setDisplayHomeAsUpEnabled(false)

        // eseguo l'inflating del layout sul fragment
        return inflater.inflate(R.layout.fragment_tracking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // metodo padre
        super.onViewCreated(view, savedInstanceState)

        // ottengo la pista selezionata dal view model
        myViewModel?.let { viewModel ->
            this.selectedPista = viewModel.getPista()
        }

        // visualizzo nome e difficoltà della pista sul fragment
        val pistaNomeTextView = view.findViewById<TextView>(R.id.trackPistaName)
        val pistaDifficultyTextView = view.findViewById<TextView>(R.id.trackPistaDifficulty)
        pistaNomeTextView.text = this.selectedPista.getNome()
        pistaDifficultyTextView.text = this.selectedPista.getDifficolta().uppercase()
        this.changeDifficultyIndicatorColor(pistaDifficultyTextView, this.selectedPista.getDifficolta())
    }

    private fun changeDifficultyIndicatorColor(textView: TextView, diff: String) {
        when(diff) {
            "Facile" -> textView.setBackgroundResource(R.color.pistaFacile)
            "Medio" -> textView.setBackgroundResource(R.color.pistaMedia)
            "Avanzato" -> textView.setBackgroundResource(R.color.black)
        }
    }
}