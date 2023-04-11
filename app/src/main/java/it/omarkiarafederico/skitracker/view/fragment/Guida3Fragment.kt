package it.omarkiarafederico.skitracker.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import it.omarkiarafederico.skitracker.R
import it.omarkiarafederico.skitracker.view.activity.MapActivity

class Guida3Fragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_guida3, container, false)

        val startBtn = view.findViewById<Button>(R.id.thirdTutorialStartBtn)
        val prevBtn = view.findViewById<Button>(R.id.thirdTutorialBtnBack)

        startBtn.setOnClickListener{
            // svuoto il back stack, in modo tale che se premo indietro non ritorno al tutorial
            this.activity?.finishAffinity()
            // avvio l'activity per la vista mappa
            val intent = Intent(activity, MapActivity::class.java)
            startActivity(intent)
        }

        prevBtn.setOnClickListener{
            // se è presente un fragment all'interno del back stack, lo vado a recuperare,
            // sostituendolo a quello corrente e togliendolo dal back stack.
            val fragmentManager = activity?.supportFragmentManager
            if (fragmentManager?.backStackEntryCount!! > 0) {
                fragmentManager?.popBackStack()
            }
        }

        return view
    }
}