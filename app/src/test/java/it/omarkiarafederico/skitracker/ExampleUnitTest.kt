package it.omarkiarafederico.skitracker

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import it.omarkiarafederico.skitracker.view.activity.AboutUsActivity
import it.omarkiarafederico.skitracker.view.activity.MapActivity
import org.junit.After
import org.junit.Test
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}

@RunWith(AndroidJUnit4::class)
class MyActivityTest {

    private lateinit var scenario: ActivityScenario<MapActivity>

    @Before
    fun setUp() {
        // Avvia lo scenario dell'attività da testare
        scenario = ActivityScenario.launch(MapActivity::class.java)
        // Inizializza Intents per la verifica delle intent
        Intents.init()
    }

    @After
    fun tearDown() {
        // Chiude lo scenario dell'attività da testare
        scenario.close()
        // Rilascia Intents dopo il test
        Intents.release()
    }

    @Test
    fun testStartActivity() {  //Per verificare il corretto funzionamento del passaggio da MapActivity
        //ad AboutUsActivity, su cui ci sono state delle problematiche

        // Esegui la funzione startActivity
        scenario.onActivity { it.startActivity(Intent(it, AboutUsActivity::class.java)) }
        // Verifica che sia stata lanciata un'Intent per l'attività MyOtherActivity
        intended(hasComponent(AboutUsActivity::class.java.name))
    }
}

