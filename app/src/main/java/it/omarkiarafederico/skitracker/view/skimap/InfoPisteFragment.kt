package it.omarkiarafederico.skitracker.view.skimap

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.omarkiarafederico.skitracker.R
import it.omarkiarafederico.skitracker.databinding.ActivityMapBinding
import it.omarkiarafederico.skitracker.databinding.FragmentInfoPisteBinding
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class InfoPisteFragment : Fragment() {
    private lateinit var skiArea: model.Comprensorio
    private lateinit var binding: FragmentInfoPisteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentInfoPisteBinding.inflate(layoutInflater)

        val myActivity = this.activity as MapActivity
        skiArea = myActivity.getComprensorioSelezionato()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_piste, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var titolo: TextView = view.findViewById(R.id.titolo)
        val numPiste: TextView = view.findViewById(R.id.numPiste)
        val impiantiRisalita: TextView = view.findViewById(R.id.impiantiRisalita)
        val max: TextView = view.findViewById(R.id.altMax)
        val min: TextView = view.findViewById(R.id.altMin)
        val sito: TextView = view.findViewById(R.id.website)

        val url = skiArea.getWebSite()

        titolo.text = skiArea.getNome()
        numPiste.text = "${skiArea.getNumPiste()}"
        impiantiRisalita.text = "${skiArea.getNumImpianti()}"
        max.text = "${skiArea.getMaxAlt()}"
        min.text = "${skiArea.getMinAlt()}"

        //gestisco la lunghezza del titolo. Se troppo lungo, per evitare
        //sovrapposizioni con altre TextView viene accorciato

        var lunghezzanome = titolo.text.length
        var titolocorto = ""
        if (lunghezzanome > 10) {
            for (i in 0..7) {
                titolocorto += titolo.text[i]
            }
            titolocorto += "..."
            titolo.text = titolocorto
        }

        //aggiungo link al sito
        sito.movementMethod = LinkMovementMethod.getInstance()
        sito.setOnClickListener {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
        }


        //Personalizzo la scritta che indica se quel comprensorio è aperto o chiuso
        val stato = view.findViewById<TextView>(R.id.stato)
        val aperto = skiArea.isOperativo()

        if (aperto) {
            stato.text = "APERTO"
            context?.let { ContextCompat.getColor(it, R.color.green) }
                ?.let { stato.setBackgroundColor(it) }
        } else {
            stato.text = "CHIUSO"
            context?.let { ContextCompat.getColor(it, R.color.red) }
                ?.let { stato.setBackgroundColor(it) }
        }

        //gestisco le scritte snowpark e piste notturne

        val snowpark = view.findViewById<TextView>(R.id.snowpark)
        val night = view.findViewById<TextView>(R.id.piste_notturne)

        val showsnow = skiArea.getSnowPark()
        val shownight = skiArea.getNight()

        if (showsnow){
            snowpark.visibility = View.VISIBLE
        } else {
            snowpark.visibility = View.GONE
        }

        if (shownight){
            night.visibility = View.VISIBLE
        } else {
            night.visibility = View.GONE
        }


        //ELENCO PISTE DISPONIBILI. Con RecyclerView.




        /*
        class MyAdapter(private val myDataset: Array<String>) :
            RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

            class MyViewHolder(val textView1: TextView, val textView2: TextView) : RecyclerView.ViewHolder(textView1)

            override fun onCreateViewHolder(parent: ViewGroup,
                                            viewType: Int): MyAdapter.MyViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.my_textviews_layout, parent, false) as LinearLayout
                val textView1 = view.findViewById<TextView>(R.id.textview1)
                val textView2 = view.findViewById<TextView>(R.id.textview2)
                return MyViewHolder(textView1, textView2)
            }

            override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
                holder.textView1.text = myDataset[position * 2]
                holder.textView2.text = myDataset[position * 2 + 1]
            }

            override fun getItemCount() = (myDataset.size + 1) / 2
        }
         */


        /*  METODO PER AGGIUNGERE UNA TEXTVIEW DA CODICE
        val textView = TextView(this)
        textView.text = "Hello, world!"
        textView.textSize = 20f

        val layout = findViewById<LinearLayout>(R.id.layout)
        layout.addView(textView)
        */

        /*
        private class TrackAdapter extends RecyclerView.Adapter<TrackViewHolder> {
        private List<Track> mTracks;

        public TrackAdapter(List<Track> tracks) {
            mTracks = tracks;
        }

        @Override
        public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.track_list_item, parent, false);
            return new TrackViewHolder(view);
        }

        @Override
        public void onBindViewHolder(TrackViewHolder holder, int position) {
            Track track = mTracks.get(position);
            holder.bind(track);
        }

        @Override
        public int getItemCount() {
            return mTracks.size();
        }
    }

    private class TrackViewHolder extends RecyclerView.ViewHolder {
        private TextView mNameTextView;
        private TextView mDifficultyTextView;

        public TrackViewHolder(View itemView) {
            super(itemView);

            mNameTextView = itemView.findViewById(R.id.track_name);
            mDifficultyTextView = itemView.findViewById(R.id.track_difficulty);
        }

        public void bind(Track track) {
            mNameTextView.setText(track.getName());
            mDifficultyTextView.setText(track.getDifficulty());
        }
    }



         */

       /* val listView = view.findViewById<ListView>(R.id.elenco_piste)
        val elenco= mutableListOf("primo","secondo","terzo")
        elenco.add("quarto")

        val arrayAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, elenco)

        listView.adapter = arrayAdapter

        */

    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch{
            val pisteList = RoomDAO(roomdb.Pista).getSkiAreaPiste
            (skiArea.getIdComprensorio)
        }
        binding.myRecyclerView.apply{
            layoutManager = LinearLayoutManager(this)
            adapter = ListAdapter().apply {
                setData(pisteList)
            }
        }
    }


}