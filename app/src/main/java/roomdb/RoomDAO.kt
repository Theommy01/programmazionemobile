/**************************************************************************************************
 * RoomDAO
 * Questo file contiene l'interfaccia del Data Access Object (DAO), che si occupa dell'interazione
 * con il database fornendo metodi per poterne manipolare i dati contenuti.
 *************************************************************************************************/

package roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LocalDatabaseDao {
    // prende in input l'oggetto di tipo Utente (tipo di dato definito nelle entità del database)
    // e lo inserisce
    @Insert(entity = Utente::class)
    fun insertLocalUserInfo(user: Utente)

    // ottiene unicamente il valore "tutorialCompletato": se questo è true (1), l'utente ha già
    // seguito il tutorial; in caso negativo, l'utente dovrà consultare il tutorial prima di usare
    // l'app
    @Query("SELECT tutorialCompletato FROM Utente WHERE id = 1")
    fun isTutorialCompletato(): Int
}