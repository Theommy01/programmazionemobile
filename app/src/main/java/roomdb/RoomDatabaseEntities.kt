/**************************************************************************************************
 * RoomDatabaseEntities
 * Questo file contiene tutte le entità del database interno all'applicazione, realizzato
 * tramite Android Room, un'implementazione semplificata di SQLite apposita per Android.
 * Tutte le entità presenti all'interno del seguente file sono trattate come delle classi Kotlin.
 *************************************************************************************************/

package roomdb

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Utente",
    foreignKeys = [
        ForeignKey(entity = Comprensorio::class, parentColumns = ["id"],
            childColumns = ["idComprensorio"])
    ])
data class Utente(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val tutorialCompletato: Boolean,
    val idComprensorio: Int?
)

@Entity(tableName = "Tracciamento",
    foreignKeys = [
        ForeignKey(entity = Utente::class, parentColumns = ["id"], childColumns = ["idUtente"]),
        ForeignKey(entity = Pista::class, parentColumns = ["id"], childColumns = ["idPista"])
    ])
data class Tracciamento (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val distanza: Float,
    val velocita: Float,
    val dislivello: Int,
    val dataOraInizio: Long,
    val dataOraFine: Long,
    val idUtente: Int,
    val idPista: Int
)

@Entity(tableName = "Pista",
    foreignKeys = [
        ForeignKey(entity = Comprensorio::class, parentColumns = ["id"],
            childColumns = ["idComprensorio"])
    ])
data class Pista (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val nome: String,
    val difficolta: String,
    val idComprensorio: Int
)

// NOTA - in seguito ad errori di compilazione di Gradle, emersi durante lo sviluppo di questo file,
// è stato necessario aggiungere il costruttore vuoto in basso
@Entity(tableName = "Comprensorio")
data class Comprensorio (
    @PrimaryKey(autoGenerate = true) var id: Int,
    var nome: String,
    var aperto: Boolean,
    var numPiste: Int,
    var numImpianti: Int,
    var website: String,
    var snowpark: Boolean,
    var pisteNotturne: Boolean,
    var lat: Double,
    var long: Double,
    var maxAltitudine: Int,
    var minAltitudine: Int
){
    constructor() : this(0, "", false, 0, 0, "",
        false, false, 0.0, 0.0, 0, 0)
}

@Entity(primaryKeys = ["idTracciamento", "idPuntoMappa"],
    foreignKeys = [
        ForeignKey(entity = Tracciamento::class, parentColumns = ["id"],
            childColumns = ["idTracciamento"]),
        ForeignKey(entity = PuntoMappa::class, parentColumns = ["id"],
            childColumns = ["idPuntoMappa"]),
    ])
data class PuntiMappaTracciamenti (
    val idTracciamento: Int,
    val idPuntoMappa: Int,
    val timestamp: Long
)

@Entity
data class PuntoMappa (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val latitudine: Double,
    val longitudine: Double
)