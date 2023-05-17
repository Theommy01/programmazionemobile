package it.omarkiarafederico.skitracker.view.skimap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import it.omarkiarafederico.skitracker.R
import roomdb.Pista

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    private var list = mutableListOf<Pista>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_info_piste, parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pista: Pista = list.get(position)  // pista = list(position) da YT
        holder.nome.text = pista.nome
        holder.difficolta.text = pista.difficolta
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(data: List<Pista>){
        list.apply{
            clear()
            addAll(data)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome = itemView.findViewById<TextView>(R.id.nomePista)
        val difficolta = itemView.findViewById<TextView>(R.id.difficoltaPista)

        //gestione colore sfondo livello di difficolta
        /*
        when (difficolta) {
            "Facile" -> {
                context?.let { ContextCompat.getColor(it, R.color.dark_blue) }
                    ?.let { difficolta.setBackgroundColor(it) }
            }
            "Medio" -> {

            }
            "Avanzato" -> {

            }
            }


         */
    }

}
