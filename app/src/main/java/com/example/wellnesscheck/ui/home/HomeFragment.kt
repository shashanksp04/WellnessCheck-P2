package com.example.wellnesscheck.ui.home

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.wellnesscheck.R
import org.w3c.dom.Text
import java.time.LocalDate
import kotlin.math.abs
import java.time.format.DateTimeFormatter


class HomeFragment : Fragment() {

    private lateinit var waterTextView: TextView
    private lateinit var caloriesTextView: TextView
    private lateinit var stepTextView: TextView
    private lateinit var sleepsTextView: TextView
    private lateinit var progressWeightBar: ProgressBar
    private lateinit var progressCalorieBar: ProgressBar
    private lateinit var weightTextView: TextView
    private lateinit var caloriesView: TextView
    private lateinit var weightPercent: TextView
    private lateinit var caloriesPercent: TextView
    private lateinit var editValuesButton: Button
    private lateinit var updateGoalButton: Button
    private lateinit var todayDate:TextView
    private lateinit var userGreeting:TextView

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize UI components
        waterTextView = root.findViewById(R.id.waterTextView)
        caloriesTextView = root.findViewById(R.id.caloriesTextView)
        stepTextView = root.findViewById(R.id.stepsTextView)
        sleepsTextView = root.findViewById(R.id.sleepTextView)
        progressWeightBar = root.findViewById(R.id.progressWeightBar)
        progressCalorieBar = root.findViewById(R.id.progressCalorieBar)
        weightTextView = root.findViewById(R.id.weightTextGoal)
        caloriesView = root.findViewById(R.id.calorieGoalText)
        weightPercent = root.findViewById(R.id.weightPercentageTextView)
        caloriesPercent = root.findViewById(R.id.caloriePercentageTextView)
        editValuesButton = root.findViewById(R.id.editValuesButton)
        updateGoalButton = root.findViewById(R.id.updateValuesButton)
        todayDate = root.findViewById(R.id.dateToday)
        userGreeting = root.findViewById(R.id.greetingUser)

        // default
        todayDate.text = getFormattedCurrentDate()
        userGreeting.text = getGreeting("John Doe")
        waterTextView.text = "2.5L"
        caloriesTextView.text = "1000 kcal"
        stepTextView.text = "2000 steps"
        sleepsTextView.text = "5 hrs"
        val currentCalories = 1000
        val dailyCalorieGoal = 1500


        val currentWeight = 70.0
        val targetWeight = 65.0
        val startingWeight = 85.0

        val weightProgress = calculateWeightProgress(currentWeight, targetWeight, startingWeight)
        progressWeightBar.progress = weightProgress
        weightPercent.text = "$weightProgress%"
        weightTextView.text = "Goal: $targetWeight kg, Current: $currentWeight kg"

        val calorieProgress = calculateCalorieProgress(currentCalories, dailyCalorieGoal)
        progressCalorieBar.progress = calorieProgress
        caloriesPercent.text = "$calorieProgress%"
        caloriesView.text = "Daily goal: $dailyCalorieGoal kcal, Current: $currentCalories"

        // Set up button to show popup dialog for editing values
        editValuesButton.setOnClickListener { showEditDialog(waterTextView, caloriesTextView,
            stepTextView,sleepsTextView,
            progressWeightBar, weightPercent, weightTextView,
            progressCalorieBar, caloriesPercent, caloriesView,
            dailyCalorieGoal, targetWeight) }

        updateGoalButton.setOnClickListener { showUpdateDialog(weightTextView, weightPercent,
            progressWeightBar, caloriesView, caloriesPercent, progressCalorieBar,
            dailyCalorieGoal, currentCalories,targetWeight, currentWeight)}
        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getFormattedCurrentDate(): String {

        val currentDate = LocalDate.now()


        val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy")


        val formattedDate = currentDate.format(formatter)

        return "Today is $formattedDate"
    }

    private fun getGreeting(name: String): String {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)

        return when (hour) {
            in 5..11 -> "Good Morning, $name!"
            in 12..17 -> "Good Afternoon, $name!"
            in 18..20 -> "Good Evening, $name!"
            else -> "Good Night, $name!"
        }
    }

    private fun calculateWeightProgress(currentWeight: Double, targetWeight: Double, startingWeight: Double): Int {
        val totalLossNeeded = abs(targetWeight - startingWeight)
        val currentLossAchieved = abs(currentWeight - startingWeight)
        return ((currentLossAchieved / totalLossNeeded) * 100).toInt()
    }

    private fun calculateCalorieProgress(currentCalories: Int, dailyGoal: Int): Int {
        return ((currentCalories.toDouble() / dailyGoal) * 100).toInt()
    }

    private fun extractDecimalFromText(text: String): Double? {
        val regex = Regex("[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)")
        val matchResult = regex.find(text)
        return matchResult?.value?.toDoubleOrNull()
    }

    private fun extractSecondInteger(input: String): Double? {
        val regex = Regex("""Goal:\s*([\d.]+)\s*kg,\s*Current:\s*([\d.]+)\s*kg""")
        val matchResult = regex.find(input)
        return matchResult?.groupValues?.get(2)?.toDoubleOrNull()
    }

    private fun extractFirstInteger(input: String): Double? {
        val regex = Regex("""Goal:\s*([\d.]+)\s*kg,\s*Current:\s*([\d.]+)\s*kg""")
        val matchResult = regex.find(input)
        return matchResult?.groupValues?.get(1)?.toDoubleOrNull()
    }

    private fun extractFirstValue(input: String): Double? {
        val regex = Regex("""Daily Goal:\s*(\d+)\s*kcal,\s*Current:\s*(\d+)\s*kcal""")
        val matchResult = regex.find(input)
        return matchResult?.groupValues?.get(1)?.toDoubleOrNull()
    }

    private fun showEditDialog(water:TextView, calorie:TextView, step:TextView,sleep:TextView,
                               progressWeight: ProgressBar, weightPercent: TextView, weightText: TextView,
                               progress:ProgressBar, caloriePercent:TextView, calorieText: TextView,
                               dailyGoal: Int, targetWeight: Double) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.popup_sliders, null)
        val waterSlider = dialogView.findViewById<SeekBar>(R.id.waterSlider)
        val caloriesSlider = dialogView.findViewById<SeekBar>(R.id.caloriesSlider)
        val stepsSlider = dialogView.findViewById<SeekBar>(R.id.stepsSlider)
        val sleepSlider = dialogView.findViewById<SeekBar>(R.id.sleepSlider)
        val weightSlider = dialogView.findViewById<SeekBar>(R.id.weightSlider)


        // Set initial slider values
        val waterText = water.text.toString()
        val waterValue = extractDecimalFromText(waterText)
        if (waterValue != null) {
             waterSlider.progress= (waterValue * 100).toInt()
        } else {
            waterSlider.progress = 250
        }

        val calorieExtractText = calorie.text.toString()
        val calExtractValue = extractDecimalFromText(calorieExtractText)
        if (calExtractValue != null) {
            caloriesSlider.progress= calExtractValue.toInt()
        } else {
            caloriesSlider.progress = 622
        }

        val sleepExText = sleep.text.toString()
        val sleepExValue = extractDecimalFromText(sleepExText)
        if (sleepExValue != null) {
            sleepSlider.progress= sleepExValue.toInt()
        } else {
            sleepSlider.progress = 2000
        }

        val stepsExText = step.text.toString()
        val stepExValue = extractDecimalFromText(stepsExText)
        if (stepExValue != null) {
            stepsSlider.progress= stepExValue.toInt()
        } else {
            stepsSlider.progress = 7
        }

        val weightExText = weightText.text.toString()
        val weightExValue = extractSecondInteger(weightExText)
        if (weightExValue != null) {
            weightSlider.progress= weightExValue.toInt()
        } else {
            weightSlider.progress = 70
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Edit Wellness Stats")
            .setView(dialogView)
            .setPositiveButton("Save") { dialog, _ ->
                // Retrieve and update values from sliders
                val waterValue = waterSlider.progress / 100.0
                val calorieValue = caloriesSlider.progress
                val stepsValue = stepsSlider.progress
                val sleepValue = sleepSlider.progress
                val weightValue = weightSlider.progress

                // Updating the displayed values
                water.text = "$waterValue L"
                calorie.text = "$calorieValue kcal"
                step.text = "$stepsValue steps"
                sleep.text = "$sleepValue hrs"

                val weightProgress = calculateWeightProgress(weightValue.toDouble(),targetWeight,
                    85.0)
                progressWeight.progress = weightProgress
                weightPercent.text = "$weightProgress%"
                weightText.text = "Goal: $targetWeight kg, Current: $weightValue kg"

                val calorieProgress = calculateCalorieProgress(calorieValue, dailyGoal)
                progress.progress = calorieProgress
                caloriePercent.text = "$calorieProgress%"
                calorieText.text = "Daily Goal: $dailyGoal kcal, Current: $calorieValue kcal"

                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun showUpdateDialog(weightText:TextView, weightPercent: TextView, weightProgress:ProgressBar,
                                 calorieText:TextView, caloriePercent: TextView, calorieProgress:ProgressBar,
                                 calorieGoal:Int, calorieValue:Int,
                                 weightGoal:Double, weightValue:Double) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(
            R.layout.popup_updategoal_sliders, null)
        val calorieSlider = dialogView.findViewById<SeekBar>(R.id.caloriesGoalSlider)
        val weightSlider = dialogView.findViewById<SeekBar>(R.id.weightGoalSlider)

        val weightExText = weightText.text.toString()
        println("$weightExText \n")
        val weightExValue = extractFirstInteger(weightExText)
        println("$weightExValue \n")
        if (weightExValue != null) {
            weightSlider.progress= weightExValue.toInt()
        } else {
            weightSlider.progress = 70
        }

        val calorieExText = calorieText.text.toString()
        println("$calorieExText \n")
        val calorieExValue = extractFirstValue(calorieExText)
        println("$calorieExValue \n")
        if (calorieExValue != null) {
            calorieSlider.progress= calorieExValue.toInt()
        } else {
            calorieSlider.progress = 1000
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Update Goal Stats")
            .setView(dialogView)
            .setPositiveButton("Save") { dialog, _ ->
                // Retrieve and update values from sliders
                val weightGoalValue = weightSlider.progress
                val calorieGoalValue = calorieSlider.progress

                // Updating the displayed values
                val weightsProgress = calculateWeightProgress(weightValue,
                    weightGoalValue.toDouble(),
                    85.0)
                val wProgress = weightsProgress.toInt()
                weightProgress.progress = wProgress
                weightPercent.text = "$wProgress%"
                weightText.text = "Goal: $weightGoalValue kg, Current: $weightValue kg"

                val caloriesProgress = calculateCalorieProgress(calorieValue, calorieGoalValue)
                calorieProgress.progress = caloriesProgress
                val cProgress = caloriesProgress.toInt()
                caloriePercent.text = "$cProgress%"
                calorieText.text = "Daily Goal: $calorieGoalValue kcal, Current: $calorieValue kcal"

                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
