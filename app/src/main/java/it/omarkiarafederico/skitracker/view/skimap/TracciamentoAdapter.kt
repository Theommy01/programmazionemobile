package it.omarkiarafederico.skitracker.view.skimap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import it.omarkiarafederico.skitracker.R
import java.time.format.DateTimeFormatter

class TracciamentoAdapter(private val tracciamentoList: ArrayList<TracciamentoItem>): RecyclerView.Adapter<TracciamentoAdapter.TracciamentoViewHolder>() {
    class TracciamentoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tracciamentoPistaNome = itemView.findViewById<TextView>(R.id.tracciamentoPistaName)
        val tracciamentoPistaDifficolta = itemView.findViewById<TextView>(R.id.tracciamentoPistaDifficulty)
        val tracciamentoAverageSpeed = itemView.findViewById<TextView>(R.id.trackAverageSpeed)
        val tracciamentoDurata = itemView.findViewById<TextView>(R.id.trackDuration)
        val tracciamentoLunghezza = itemView.findViewById<TextView>(R.id.trackingDistance)
        val tracciamentoDislivello = itemView.findViewById<TextView>(R.id.trackingDislivello)
        val tracciamentoDataOra = itemView.findViewById<TextView>(R.id.trackDateTime)
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
        holder.tracciamentoPistaDifficolta.text = tracciamento.difficolta
        holder.tracciamentoAverageSpeed.text = tracciamento.velocitaMedia.toString()
        holder.tracciamentoDurata.text = tracciamento.durata
        holder.tracciamentoLunghezza.text = tracciamento.lunghezza.toString()
        holder.tracciamentoDislivello.text = tracciamento.dislivello.toString()
        holder.tracciamentoDataOra.text = tracciamento.dataOra.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))
    }
}