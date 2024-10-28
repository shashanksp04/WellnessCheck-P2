package com.example.wellnesscheck.ui.dashboard

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.wellnesscheck.R
import com.example.wellnesscheck.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    // Sample list of activities
    private val activities = mutableListOf("Select an activity...", "Running: 30 mins, Moderate", "Walking: 45 mins, Light")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Setup activity spinner and add activity button
        setupActivityCard()


        return root
    }

    private fun setupActivityCard() {
        // Initialize the spinner with activities
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, activities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.activitySpinner.adapter = adapter

        // Expand the stats section when an activity is selected
        binding.activitySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (position > 0) { // Ignore "Select an activity..." option
                    binding.statsSection.visibility = View.VISIBLE
                    binding.activityStats.text = activities[position]
                } else {
                    binding.statsSection.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        // Show add activity popup when button is clicked
        binding.addActivityButton.setOnClickListener {
            showAddActivityPopup(adapter)
        }
    }

    private fun showAddActivityPopup(adapter: ArrayAdapter<String>) {
        // Inflate the popup layout
        val inflater = LayoutInflater.from(requireContext())
        val popupView = inflater.inflate(R.layout.popup_add_activity, null)

        // Initialize the popup elements
        val activityTypeSpinner: Spinner = popupView.findViewById(R.id.activity_type_spinner)
        val durationInput: EditText = popupView.findViewById(R.id.activity_duration)
        val intensitySpinner: Spinner = popupView.findViewById(R.id.intensity_spinner)
        val saveButton: Button = popupView.findViewById(R.id.save_activity_button)

        // Create AlertDialog for the popup
        val dialog = AlertDialog.Builder(requireContext())
            .setView(popupView)
            .create()

        saveButton.setOnClickListener {
            val activityType = activityTypeSpinner.selectedItem.toString()
            val duration = durationInput.text.toString()
            val intensity = intensitySpinner.selectedItem.toString()

            if (activityType.isNotEmpty() && duration.isNotEmpty() && intensity.isNotEmpty()) {
                // Add new activity to the spinner list
                val newActivity = "$activityType: $duration mins, $intensity"
                activities.add(newActivity)
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
