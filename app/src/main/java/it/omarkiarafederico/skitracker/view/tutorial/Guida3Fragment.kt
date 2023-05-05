package it.omarkiarafederico.skitracker.view.tutorial

import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import it.omarkiarafederico.skitracker.R
import it.omarkiarafederico.skitracker.view.selezionecomprensorio.SelezioneComprensorio
import org.osmdroid.views.MapView
import roomdb.RoomHelper
import roomdb.Utente

class Guida3Fragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_guida3, container, false)

        val startBtn = view.findViewById<Button>(R.id.thirdTutorialStartBtn)
        val prevBtn = view.findViewById<Button>(R.id.thirdTutorialBtnBack)

        startBtn.setOnClickListener{
            // svuoto il back stack, in modo tale che se premo indietro non ritorno al tutorial
            this.activity?.finishAffinity()

            // scrivo sul database le info sull'utente locale che sta eseguendo l'app (id del
            // telefono e il fatto che abbia già visto il tutorial)
            val db = RoomHelper().getDatabaseObject(this.requireContext())

            var intent = Intent(activity, SelezioneComprensorio::class.java)
            val phoneId = Settings.Secure.getString(requireActivity().contentResolver,
                Settings.Secure.ANDROID_ID)

            try {
                db.localDatabaseDao()
                    .insertNewLocalUserInfo(Utente(phoneId, true, null))
            } catch (e: SQLiteConstraintException) {
                if (db.localDatabaseDao().getIdComprensorio() != null)
                    intent = Intent(activity, MapView::class.java)
            }

            startActivity(intent)
        }

        prevBtn.setOnClickListener{
            // se è presente un fragment all'interno del back stack, lo vado a recuperare,
            // sostituendolo a quello corrente e togliendolo dal back stack.
            val fragmentManager = activity?.supportFragmentManager
            if (fragmentManager?.backStackEntryCount!! > 0) {
                fragmentManager.popBackStack()
            }
        }

        return view
    }
}