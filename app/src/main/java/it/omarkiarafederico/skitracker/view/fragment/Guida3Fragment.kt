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
    // TODO: (per Omar) commentare questo codice!!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_guida3, container, false)
        val nextBtn = view.findViewById<Button>(R.id.button9)
        nextBtn.setOnClickListener{
            /*
            val fragment = Guida3Fragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.guida2, fragment)?.commit()

             */
            val Intent = Intent(activity, MapActivity::class.java)
            startActivity(Intent)
        }

        val prevBtn = view.findViewById<Button>(R.id.button8)
        prevBtn.setOnClickListener{
            val fragment = Guida2Fragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.guida3, fragment)?.commit()
        }

        return view
    }
}