package it.omarkiarafederico.skitracker

import android.content.Intent
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
import it.omarkiarafederico.skitracker.view.routeTracking.RouteTrackingActivity
import it.omarkiarafederico.skitracker.view.skimap.AboutUsActivity
import it.omarkiarafederico.skitracker.view.skimap.InfoPisteFragment
import it.omarkiarafederico.skitracker.view.skimap.MapActivity
import it.omarkiarafederico.skitracker.view.skimap.MappaFragment
import org.junit.After
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.google.android.material.floatingactionbutton.FloatingActionButton

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation.findNavController
import androidx.navigation.testing.TestNavHostController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Assert.assertEquals

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

private lateinit var activityScenario: ActivityScenario<MapActivity>


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

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(RouteTrackingActivity::class.java)

    @Before
    fun setup() {
        activityScenario = ActivityScenario.launch(MapActivity::class.java)
    }

    @Test
    fun fabClicTest() { //Test per verificare il corretto passaggio all'Activity di tracciamento
        // Avvia il fragment in un contenitore
        val scenario = launchFragmentInContainer<MappaFragment>()

        // Simula un clic sul FAB
        onView(withId(R.id.addRecordFAB)).perform(click())

        // Verifica che la navigazione sia avvenuta correttamente utilizzando un Intent
        activityScenario.onActivity { activity ->
            val intent = activity.intent
            assertEquals(RouteTrackingActivity::class.java.name, intent.component?.className)
        }

        /*

    @Test
    fun testFabClick() {
        // Avvia il fragment
        val scenario = FragmentScenario.launchInContainer(MappaFragment::class.java)

        // Esegui l'azione sul thread principale
        scenario.onFragment { fragment ->
            val fab = fragment.requireView().findViewById<FloatingActionButton>(R.id.addRecordFAB)

            // Simula il click sul FAB nel tuo fragment
            fab.performClick()

            // Verifica che sia stata avviata la nuova activity
            val expectedIntent = Intent(fragment.requireContext(), RouteTrackingActivity::class.java)
            val actualIntent = Shadows.shadowOf(fragment.requireActivity()).nextStartedActivity
            assertThat(actualIntent).isEqualTo(expectedIntent)
        }
    }
    */

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

}
