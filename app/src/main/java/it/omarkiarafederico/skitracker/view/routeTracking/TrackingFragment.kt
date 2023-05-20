package it.omarkiarafederico.skitracker.view.routeTracking

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import it.omarkiarafederico.skitracker.R

class TrackingFragment : Fragment() {
    private lateinit var viewModel: TrackingFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tracking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[TrackingFragmentViewModel::class.java]
        val test = view.findViewById<TextView>(R.id.banane_fritte)
        test.text = viewModel.getPista().getNome()
    }
}