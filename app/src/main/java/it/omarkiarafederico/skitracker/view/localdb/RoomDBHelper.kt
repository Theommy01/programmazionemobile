package it.omarkiarafederico.skitracker.view.localdb

import androidx.room.*

// definizione entità del database
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
            ForeignKey(entity = Comprensorio::class, parentColumns = ["id"], childColumns = ["idComprensorio"])
        ])
data class Pista (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val nome: String,
    val difficolta: String,
    val idComprensorio: Int
)

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

// data access object (DAO) per l'interazione col database
@Dao
interface LocalDatabaseDao {
    @Insert(entity = Utente::class)
    fun insertLocalUserInfo(user: Utente)

    @Query("SELECT tutorialCompletato FROM Utente WHERE id = 1")
    fun isTutorialCompletato(): Int
}

// classe per il collegamento al database
@Database(entities = [
    Utente::class, Tracciamento::class, Pista::class, Comprensorio::class, PuntoMappa::class,
    PuntiMappaTracciamenti::class
], version = 1)
abstract class LocalDB : RoomDatabase() {
    abstract fun localDatabaseDao(): LocalDatabaseDao
}