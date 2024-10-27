package com.example.wellnesscheck.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.wellnesscheck.R

class HomeFragment : Fragment() {

    private lateinit var waterTextView: TextView
    private lateinit var caloriesTextView: TextView
    private lateinit var progressWeightBar: ProgressBar
    private lateinit var progressCalorieBar: ProgressBar
    private lateinit var weightTextView : TextView
    private lateinit var caloriesView : TextView
    private lateinit var weightPercent : TextView
    private lateinit var caloriesPercent : TextView

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

        // Set initial values (you can change these based on dynamic data)
        waterTextView.text = "2.5L"
        caloriesTextView.text = "1800 kcal"
        // Example data for current and target weights and calorie goals
        val currentWeight = 70.0 // kg
        val targetWeight = 65.0 // kg
        val startingWeight = 75.0 // kg (example starting weight)

        val currentCalories = 1000 // kcal consumed today
        val dailyCalorieGoal = 1500 // kcal goal

        // Calculate weight progress as a percentage of target weight loss achieved
        val weightProgress = calculateWeightProgress(currentWeight, targetWeight, startingWeight)
        progressWeightBar.progress = weightProgress
        weightPercent.text = "$weightProgress%"
        weightTextView.text = "Current: $currentWeight kg, Target: $targetWeight kg"

        // Calculate calorie progress as a percentage of daily goal achieved
        val calorieProgress = calculateCalorieProgress(currentCalories, dailyCalorieGoal)
        progressCalorieBar.progress = calorieProgress
        caloriesPercent.text = "$calorieProgress%"
        caloriesView.text = "Daily goal: $dailyCalorieGoal"

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
}