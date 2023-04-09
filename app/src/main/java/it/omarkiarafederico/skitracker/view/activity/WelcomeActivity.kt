package it.omarkiarafederico.skitracker.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import it.omarkiarafederico.skitracker.R

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val saltaButton = findViewById<Button>(R.id.button2)
        saltaButton.setOnClickListener{
            val Intent = Intent(this, MapActivity::class.java)
            startActivity(Intent)
        }
    }


}