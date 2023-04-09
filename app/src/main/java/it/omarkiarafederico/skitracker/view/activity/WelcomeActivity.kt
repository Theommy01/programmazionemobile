package it.omarkiarafederico.skitracker.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import it.omarkiarafederico.skitracker.R
import it.omarkiarafederico.skitracker.view.fragment.Guida1Fragment
import it.omarkiarafederico.skitracker.view.fragment.WelcomeFragment

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)


        val tutorialButton = findViewById<Button>(R.id.button)
        val saltaButton = findViewById<Button>(R.id.button2)

        tutorialButton.setOnClickListener{
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.welcome, Guida1Fragment()).commit()
        }


        saltaButton.setOnClickListener{
            val Intent = Intent(this, MapActivity::class.java)
            startActivity(Intent)
        }
    }


}