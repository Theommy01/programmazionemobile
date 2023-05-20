package it.omarkiarafederico.skitracker.view.routeTracking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.omarkiarafederico.skitracker.R
import it.omarkiarafederico.skitracker.view.skimap.PistaAdapter
import it.omarkiarafederico.skitracker.view.skimap.PistaItem
import model.Comprensorio
import model.Pista

class RouteSelectionFragment : Fragment() {
    private lateinit var skiArea: Comprensorio

    private lateinit var recyclerView: RecyclerView
    private lateinit var pistaAdapter: PistaAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val myActivity = this.activity as RouteTrackingActivity
        skiArea = myActivity.getSkiArea()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_route_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.trackingPistaSelectionRecyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())

        val pisteItemList = ArrayList<PistaItem>()
        for (pistaItem in skiArea.getListaPiste())
            pisteItemList.add(PistaItem(pistaItem.getNome(), pistaItem.getDifficolta()))

        pistaAdapter = PistaAdapter(pisteItemList)
        recyclerView.adapter = pistaAdapter

        pistaAdapter.onItemClick = {
            // TODO - sta roba andrà tolta e sostitutia con quella uffucuale
            val viewModel = ViewModelProvider(requireActivity())[TrackingFragmentViewModel::class.java]
            val pistacchio = roomdb.Pista(10, "dsjkf", "sadasd", 1)
            viewModel.setPista(Pista(pistacchio))

            val fragmentManager = this.parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.route_sel_fragment_container, TrackingFragment())
            fragmentTransaction.commit()
        }
    }
}