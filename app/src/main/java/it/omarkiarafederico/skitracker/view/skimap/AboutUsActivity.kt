package it.omarkiarafederico.skitracker.view.skimap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import it.omarkiarafederico.skitracker.R

class AboutUsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        supportActionBar?.title = getString(R.string.aboutUsActivityTitle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}