package com.example.wellnesscheck.ui.notifications

import android.os.Bundle
import android.service.autofill.BatchUpdates
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wellnesscheck.GraphItem
import com.example.wellnesscheck.GraphRecyclerAdapter
import com.example.wellnesscheck.R
import com.example.wellnesscheck.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    private lateinit var weightEdit: EditText
    private lateinit var heightEdit: EditText
    private lateinit var massEdit: EditText
    private lateinit var fatEdit: EditText
    private lateinit var updateGraph: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        weightEdit = binding.weightUpdate
        heightEdit = binding.heightUpdate
        massEdit = binding.massUpdate
        fatEdit = binding.fatUpdate
        updateGraph = binding.updateDataButton

        updateGraph.setOnClickListener{updateValue(weightEdit,heightEdit, massEdit, fatEdit)}
        return binding.root
    }

    private fun updateValue(weightVal : EditText, heightVal: EditText, massVal: EditText,
                            fatVal: EditText){
        weightVal.setText("")
        weightVal.hint = "Enter your current weight (kg) here"

        heightVal.setText("")
        heightVal.hint = "Enter your current height (in cm) here"

        massVal.setText("")
        massVal.hint = "Enter your known current Muscle Mass here"

        fatVal.setText("")
        fatVal.hint = "Enter your current known Body Fat % here"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // List of graphs to display in RecyclerView
        val graphs = listOf(
            GraphItem("Weight Over Time", "72 kg", "Last 6 months +2%", R.drawable.graph),
            GraphItem("BMI Changes", "24.5", "Last 6 months -1%", R.drawable.graph),
            GraphItem("Body Fat Percentage", "20%", "Last 6 months -0.5%", R.drawable.graph),
            GraphItem("Muscle Mass", "52 kg", "Last 6 months +1.5%", R.drawable.graph)
        )

        // Set up RecyclerView with a horizontal layout and GraphRecyclerAdapter
        binding.recyclerViewGraphs.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false)
        val graphRecyclerAdapter = GraphRecyclerAdapter(graphs)
        binding.recyclerViewGraphs.adapter = graphRecyclerAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
