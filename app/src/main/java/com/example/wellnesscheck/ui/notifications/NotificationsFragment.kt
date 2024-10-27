package com.example.wellnesscheck.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wellnesscheck.GraphItem
import com.example.wellnesscheck.GraphRecyclerAdapter
import com.example.wellnesscheck.R
import com.example.wellnesscheck.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
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
        binding.recyclerViewGraphs.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val graphRecyclerAdapter = GraphRecyclerAdapter(graphs)
        binding.recyclerViewGraphs.adapter = graphRecyclerAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
