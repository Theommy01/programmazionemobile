package it.omarkiarafederico.skitracker.view.skimap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import it.omarkiarafederico.skitracker.R

class PistaAdapter(private val pisteList: ArrayList<PistaItem>): RecyclerView.Adapter<PistaAdapter.PistaViewHolder>() {
    var onItemClick: ((PistaItem) -> Unit)? = null

    class PistaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nomePista: TextView = itemView.findViewById(R.id.pista_item_name)
        val difficoltaPista: TextView = itemView.findViewById(R.id.pista_item_difficulty)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PistaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pista_item, parent, false)
        return PistaViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pisteList.size
    }

    override fun onBindViewHolder(holder: PistaViewHolder, position: Int) {
        val pista = pisteList[position]

        holder.nomePista.text = pista.nome
        holder.difficoltaPista.text = pista.difficolta.uppercase()

        when(pista.difficolta) {
            "Facile" -> holder.difficoltaPista.setBackgroundResource(R.color.pistaFacile)
            "Medio" -> holder.difficoltaPista.setBackgroundResource(R.color.pistaMedia)
            "Avanzato" -> holder.difficoltaPista.setBackgroundResource(R.color.black)
        }

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(pista)
        }
    }
}