package it.omarkiarafederico.skitracker.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import it.omarkiarafederico.skitracker.R

class Guida1Fragment : Fragment() {
    // TODO: (per Omar) commentare questo codice!!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
      //  return inflater.inflate(R.layout.fragment_guida1, container, false)
      val view = inflater.inflate(R.layout.fragment_guida1, container, false)
      val nextBtn = view.findViewById<Button>(R.id.button5)
        nextBtn.setOnClickListener{
            val fragment = Guida2Fragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.guida1, fragment)?.commit()
        }

      val prevBtn = view.findViewById<Button>(R.id.button3)
      prevBtn.setOnClickListener{
          val fragment = WelcomeFragment()
          val transaction = fragmentManager?.beginTransaction()
          transaction?.replace(R.id.guida1, fragment)?.commit()
      }

      return view
    }
}