package it.omarkiarafederico.skitracker.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import it.omarkiarafederico.skitracker.R

class Guida2Fragment : Fragment() {
    // TODO: (per Omar) commentare questo codice!!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_guida2, container, false)
        val nextBtn = view.findViewById<Button>(R.id.button7)
        nextBtn.setOnClickListener{
            val fragment = Guida3Fragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.guida2, fragment)?.commit()
        }

        val prevBtn = view.findViewById<Button>(R.id.button6)
        prevBtn.setOnClickListener{
            val fragment = Guida1Fragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.guida2, fragment)?.commit()
        }

        return view
    }
}