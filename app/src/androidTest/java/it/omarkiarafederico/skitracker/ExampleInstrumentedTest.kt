package it.omarkiarafederico.skitracker

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import it.omarkiarafederico.skitracker.view.skimap.AboutUsActivity
import it.omarkiarafederico.skitracker.view.skimap.InfoPisteFragment
import it.omarkiarafederico.skitracker.view.skimap.MapActivity
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("it.omarkiarafederico.skitracker", appContext.packageName)
    }
}

@RunWith(AndroidJUnit4::class)
class GotoAboutUsActivityTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(MapActivity::class.java)

    @Test
    fun testFragmentChange() { // Testa il cambio di Fragment cliccando sulla Bottom Navigation Bar
        val fragmentManager: FragmentManager = getActivity()?.supportFragmentManager
            ?: throw IllegalStateException("FragmentManager is null")

        val currentFragment = getCurrentFragment(fragmentManager)
        val newFragment = InfoPisteFragment()


        // Esegui la transazione del Fragment
        fragmentManager.beginTransaction()
            .replace(R.id.mappaFragment, newFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun getActivity(): FragmentActivity? {
        return activityTestRule.activity
    }

    private fun getCurrentFragment(fragmentManager: FragmentManager): Fragment? {
        return fragmentManager.findFragmentById(R.id.mappaFragment)
    }
    /*

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
    fun testButtonClick() {
        /*
        /*
        // Simula l'apertura del menù a tendina
        Espresso.onView(ViewMatchers.withId(R.id.menuitem)).perform(ViewActions.click())

        // Simula il click su un pulsante all'interno del menù a tendina
        Espresso.onView(ViewMatchers.withId(R.id.about_us_item)).perform(ViewActions.click())

        // Verifica che sia stata lanciata un'Intent per l'attività desiderata
        Intents.intended(IntentMatchers.hasComponent(AboutUsActivity::class.java.name))
        */
        // onView(withId(R.id.btnSkipTutorial)).perform(click())

        openActionBarOverflowOrOptionsMenu(ApplicationProvider.getApplicationContext())
        onView(withId(R.id.menuitem)).perform(click())
        onView(withId(R.id.about_us_item)).perform(click())
        intended(hasComponent(AboutUsActivity::class.java.name))
         */
    }

     */

}
