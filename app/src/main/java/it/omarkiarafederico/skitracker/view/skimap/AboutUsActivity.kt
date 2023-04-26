package it.omarkiarafederico.skitracker.view.skimap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.view.isVisible
import it.omarkiarafederico.skitracker.R

class AboutUsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        val back= findViewById<Button>(R.id.back)

        back.setOnClickListener {
            val Intent = Intent(this, MapActivity::class.java)
            startActivity(Intent)
        }
    }


}