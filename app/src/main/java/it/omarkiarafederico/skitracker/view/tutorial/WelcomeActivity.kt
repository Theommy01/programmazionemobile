package it.omarkiarafederico.skitracker.view.tutorial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import it.omarkiarafederico.skitracker.R

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // nascondo la titleBar (che sarebbe posta in alto)
        // (notare il "?", che viene messo per evitare che il programma crashi nel caso la
        // title bar non dovesse essere presente e si avrebbe quindi una NullPointerException)
        supportActionBar?.hide()
    }


}