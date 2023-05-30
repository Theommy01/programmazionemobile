package it.omarkiarafederico.skitracker.view.skimap

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import it.omarkiarafederico.skitracker.R
import java.time.format.DateTimeFormatter

class TracciamentoAdapter(private val tracciamentoList: ArrayList<TracciamentoItem>): RecyclerView.Adapter<TracciamentoAdapter.TracciamentoViewHolder>() {
    class TracciamentoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tracciamentoPistaNome: TextView = itemView.findViewById(R.id.tracciamentoPistaName)
        val tracciamentoPistaDifficolta: TextView = itemView.findViewById(R.id.tracciamentoPistaDifficulty)
        val tracciamentoAverageSpeed: TextView = itemView.findViewById(R.id.trackAverageSpeed)
        val tracciamentoDurata: TextView = itemView.findViewById(R.id.trackDuration)
        val tracciamentoLunghezza: TextView = itemView.findViewById(R.id.trackingDistance)
        val tracciamentoDislivello: TextView = itemView.findViewById(R.id.trackingDislivello)
        val tracciamentoDataOra: TextView = itemView.findViewById(R.id.trackDateTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracciamentoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tracciamento_item, parent, false)
        return TracciamentoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tracciamentoList.size
    }

    override fun onBindViewHolder(holder: TracciamentoViewHolder, position: Int) {
        val tracciamento = tracciamentoList[position]

        holder.tracciamentoPistaNome.text = tracciamento.nome
        holder.tracciamentoAverageSpeed.text = "${tracciamento.velocitaMedia} km/h"
        holder.tracciamentoDurata.text = tracciamento.durata
        holder.tracciamentoLunghezza.text = "${tracciamento.lunghezza} mt"
        holder.tracciamentoDislivello.text = "${tracciamento.dislivello} mt"
        holder.tracciamentoDataOra.text = tracciamento.dataOra.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))

        when(tracciamento.difficolta) {
            "novice", "Novizio" -> {
                holder.tracciamentoPistaDifficolta.setBackgroundResource(R.color.white)
                holder.tracciamentoPistaDifficolta.setTextColor(Color.parseColor("#000000"))
                holder.tracciamentoPistaDifficolta.text = "Novizio"
            }

            "easy", "Facile" -> {
                holder.tracciamentoPistaDifficolta.setBackgroundResource(R.color.pistaFacile)
                holder.tracciamentoPistaDifficolta.text = "Facile"
            }
            "intermediate", "Medio" -> {
                holder.tracciamentoPistaDifficolta.setBackgroundResource(R.color.pistaMedia)
                holder.tracciamentoPistaDifficolta.text = "Medio"
            }
            "advanced", "Avanzato" -> {
                holder.tracciamentoPistaDifficolta.setBackgroundResource(R.color.black)
                holder.tracciamentoPistaDifficolta.text = "Avanzato"
            }
        }
    }
}