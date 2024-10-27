package com.example.wellnesscheck.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.wellnesscheck.R

class HomeFragment : Fragment() {

    private lateinit var waterTextView: TextView
    private lateinit var caloriesTextView: TextView
    private lateinit var progressWeightBar: ProgressBar
    private lateinit var progressCalorieBar: ProgressBar
    private lateinit var weightTextView: TextView
    private lateinit var caloriesView: TextView
    private lateinit var weightPercent: TextView
    private lateinit var caloriesPercent: TextView
    private lateinit var editValuesButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize UI components
        waterTextView = root.findViewById(R.id.waterTextView)
        caloriesTextView = root.findViewById(R.id.caloriesTextView)
        progressWeightBar = root.findViewById(R.id.progressWeightBar)
        progressCalorieBar = root.findViewById(R.id.progressCalorieBar)
        weightTextView = root.findViewById(R.id.weightTextGoal)
        caloriesView = root.findViewById(R.id.calorieGoalText)
        weightPercent = root.findViewById(R.id.weightPercentageTextView)
        caloriesPercent = root.findViewById(R.id.caloriePercentageTextView)
        editValuesButton = root.findViewById(R.id.editValuesButton) // Add button in XML layout

        // Set initial values
        waterTextView.text = "2.5L"
        caloriesTextView.text = "1800 kcal"

        val currentWeight = 70.0
        val targetWeight = 65.0
        val startingWeight = 75.0
        val currentCalories = 1000
        val dailyCalorieGoal = 1500

        // Calculate initial weight and calorie progress
        val weightProgress = calculateWeightProgress(currentWeight, targetWeight, startingWeight)
        progressWeightBar.progress = weightProgress
        weightPercent.text = "$weightProgress%"
        weightTextView.text = "Current: $currentWeight kg, Target: $targetWeight kg"

        val calorieProgress = calculateCalorieProgress(currentCalories, dailyCalorieGoal)
        progressCalorieBar.progress = calorieProgress
        caloriesPercent.text = "$calorieProgress%"
        caloriesView.text = "Daily goal: $dailyCalorieGoal kcal"

        // Set up button to show popup dialog for editing values
        editValuesButton.setOnClickListener { showEditDialog() }

        return root
    }

    private fun calculateWeightProgress(currentWeight: Double, targetWeight: Double, startingWeight: Double): Int {
        val totalLossNeeded = startingWeight - targetWeight
        val currentLossAchieved = startingWeight - currentWeight
        return ((currentLossAchieved / totalLossNeeded) * 100).toInt()
    }

    private fun calculateCalorieProgress(currentCalories: Int, dailyGoal: Int): Int {
        return ((currentCalories.toDouble() / dailyGoal) * 100).toInt()
    }

    private fun showEditDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.popup_sliders, null)
        val waterSlider = dialogView.findViewById<SeekBar>(R.id.waterSlider)
        val caloriesSlider = dialogView.findViewById<SeekBar>(R.id.caloriesSlider)
        val stepsSlider = dialogView.findViewById<SeekBar>(R.id.stepsSlider)
        val sleepSlider = dialogView.findViewById<SeekBar>(R.id.sleepSlider)


        // Set initial slider values
        waterSlider.progress = (2.5 * 100).toInt() // Example: 2.5 liters as 250
        caloriesSlider.progress = 1800
        stepsSlider.progress = 5000
        sleepSlider.progress = 7

        AlertDialog.Builder(requireContext())
            .setTitle("Edit Wellness Stats")
            .setView(dialogView)
            .setPositiveButton("Save") { dialog, _ ->
                // Retrieve and update values from sliders
                val waterValue = waterSlider.progress / 100.0
                val calorieValue = caloriesSlider.progress
                val stepsValue = stepsSlider.progress
                val sleepValue = sleepSlider.progress

                // Update the displayed values

                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
