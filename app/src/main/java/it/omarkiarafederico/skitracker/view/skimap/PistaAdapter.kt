package it.omarkiarafederico.skitracker.view.skimap

import android.graphics.Color
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
            "novice", "Novizio" -> {
                holder.difficoltaPista.setBackgroundResource(R.color.white)
                holder.difficoltaPista.setTextColor(Color.parseColor("#000000"))
                holder.difficoltaPista.text = "Novizio"
            }

            "easy", "Facile" -> { holder.difficoltaPista.setBackgroundResource(R.color.pistaFacile)
                holder.difficoltaPista.text = "Facile"
            }
            "intermediate", "Medio" -> {
                holder.difficoltaPista.setBackgroundResource(R.color.pistaMedia)
                holder.difficoltaPista.text = "Medio"
            }
            "advanced", "Avanzato" -> {
                holder.difficoltaPista.setBackgroundResource(R.color.black)
                holder.difficoltaPista.text = "Avanzato"
            }
        }

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(pista)
        }
    }
}