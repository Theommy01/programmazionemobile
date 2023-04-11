package it.omarkiarafederico.skitracker.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction
import it.omarkiarafederico.skitracker.R
import it.omarkiarafederico.skitracker.view.activity.MapActivity

class WelcomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_welcome, container, false)

        val tourBtn = view.findViewById<Button>(R.id.btnStartTutorial)
        val skipBtn = view.findViewById<Button>(R.id.btnSkipTutorial)

        tourBtn.setOnClickListener {
            val fragment = Guida1Fragment()
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            // vado a inserire il fragment corrente nel back stack, in modo tale che posso tornare
            // al passo precedente anche solamente premendo il tasto Back di android
            fragmentTransaction?.addToBackStack("tutorialFragmentWelcome")
            // imposto una transizione durante il cambio di fragment
            fragmentTransaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            // vado a rimpiazzare il fragment corrente (welcome) con quello nuovo (passo 1)
            fragmentTransaction?.replace(R.id.tutorialFragmentsContainerView, fragment)?.commit()
        }

        skipBtn.setOnClickListener {
            // svuoto il back stack, in modo tale che se premo indietro non ritorno al tutorial
            this.activity?.finishAffinity()
            // avvio l'activity per la vista mappa
            val intent = Intent(activity, MapActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}