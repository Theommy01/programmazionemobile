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

class WelcomeFragment : Fragment() {
    // TODO: (per Omar) commentare questo codice!!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_welcome, container, false)
        val tourBtn = view.findViewById<Button>(R.id.btnStartTutorial)
        val nextBtn = view.findViewById<Button>(R.id.btnSkip)
        tourBtn.setOnClickListener {
            val fragment = Guida1Fragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.inizio, fragment)?.commit()
        }
        nextBtn.setOnClickListener {
            val Intent = Intent(activity, MapActivity::class.java)
            startActivity(Intent)
        }



        return view
    }
}