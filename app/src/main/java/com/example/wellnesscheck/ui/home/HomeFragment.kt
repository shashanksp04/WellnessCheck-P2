package com.example.wellnesscheck.ui.home

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

        // Set initial values (you can change these based on dynamic data)
        waterTextView.text = "2.5L"
        caloriesTextView.text = "1800 kcal"
        progressWeightBar.progress = 25
        progressCalorieBar.progress = 25

        return root
    }
}