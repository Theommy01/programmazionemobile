package it.omarkiarafederico.skitracker.view.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import it.omarkiarafederico.skitracker.R
import it.omarkiarafederico.skitracker.view.activity.MapActivity

/*
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Guida1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */

 */
class Guida1Fragment : Fragment() {

    /*

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    */

    @SuppressLint("SuspiciousIndentation")
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

    /*

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Guida1Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
                Guida1Fragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

     */
}