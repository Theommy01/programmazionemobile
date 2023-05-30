package model

class Tracciamento(var id: Int, var distanza: Float, var velocita: Float, var dislivello: Int,
                   var dataOraInizio: Long, var dataOraFine: Long, var pistaNome: String, var pistaDifficolta: String) {
    fun getDurationString(): String {
        val seconds = this.dataOraFine - this.dataOraInizio

        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60

        val formattedHours = String.format("%02d", hours)
        val formattedMinutes = String.format("%02d", minutes)

        return "$formattedHours:$formattedMinutes"
    }
}