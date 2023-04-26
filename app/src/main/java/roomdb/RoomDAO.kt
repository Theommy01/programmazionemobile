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
    fun insertNewLocalUserInfo(user: Utente)

    // ottiene unicamente il valore "tutorialCompletato": se questo è true (1), l'utente ha già
    // seguito il tutorial; in caso negativo, l'utente dovrà consultare il tutorial prima di usare
    // l'app
    @Query("SELECT tutorialCompletato FROM Utente")
    fun isTutorialCompletato(): Int

    // ottiene l'ID del comprensorio selezionato. Se non è stato selezionato alcun comprensorio,
    // verrà ritornato NULL.
    @Query("SELECT idComprensorio FROM Utente")
    fun getIdComprensorio(): Int?

    // prende in input l'oggetto di tipo Comprensorio (tipo di dato definito nelle entità del database)
    // e lo inserisce
    @Insert(entity = Comprensorio::class)
    fun insertNewComprensorio(comp: Comprensorio)

    // quando l'utente va a selezionare un (nuovo) comprensorio, l'id del comprensorio selezionato
    // viene aggiornato con questa query
    @Query("UPDATE Utente SET idComprensorio = :newSkiAreaId")
    fun modificaComprensorioSelezionato(newSkiAreaId: Int)

    // ottiene i dettagli del comprensorio avente l'id fornito
    @Query("SELECT * FROM Comprensorio WHERE id = :skiAreaId")
    fun getDettagliComprensorio(skiAreaId: Int): Comprensorio
}