package com.example.gymapp_final

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State



open class SharedViewModel : ViewModel() {
    // Water intake in cups
    private val _waterCups = MutableStateFlow(0)
    val waterCups: StateFlow<Int> = _waterCups

    // Step count
    private val _stepsCount = MutableStateFlow(0)
    val stepsCount: StateFlow<Int> = _stepsCount

    // Sun exposure in hours (as float
    private val _sunExposure = MutableStateFlow(0f)  // Float type for hours
    val sunExposure: StateFlow<Float> = _sunExposure

    private val _exerciseCalories = MutableStateFlow(0)
    val exerciseCalories: StateFlow<Int> = _exerciseCalories

    private val _userName = mutableStateOf("User")
    val userName: State<String> get() = _userName

    // Derived: Vitamin D in IU
    val vitaminD: StateFlow<Int> = _sunExposure.map { hours ->
        (hours * 200).toInt()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 0)


    // Derived: Calories burned
    val stepCalories: StateFlow<Int> = _stepsCount.map { steps -> // to map κανει transform το stepcount σε κατι αλλο, για εδω calories
        (steps / 1000) * 40  // 1000 steps → 40 kcal
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 0)// to SharingStarted.Eagerly το βαζουμε για να μαζεψουμε data right away
    // me to viewModelScope kanoume update to viewmodel me ta calories


    // Total burned calories = from steps + exercises add screen
    val kcalBurned: StateFlow<Int> = combine(_stepsCount, _exerciseCalories) { steps, exerciseKcal ->
        val stepsKcal = (steps / 1000) * 40
        stepsKcal + exerciseKcal
    }.stateIn(viewModelScope, SharingStarted.Eagerly, 0)

    private val _kcalGoal = MutableStateFlow(2000) // Default goal for kcal
    val kcalGoal: StateFlow<Int> = _kcalGoal



    // Update methods
    fun setSteps(count: Int) {
        _stepsCount.value = count
    }

    fun setSunExposure(hours: Float) {
        _sunExposure.value = hours
    }

    fun incrementWater() {
        _waterCups.value++
    }

    fun decrementWater() {
        if (_waterCups.value > 0) {
            _waterCups.value--
        }
    }

    fun setWater(count: Int) {
        _waterCups.value = count
    }

    fun setExerciseCalories(minutes: Int, exerciseCount: Int) {
        // 100 calories per 10 minutes for each exercise
        val calories = (minutes / 10.0 * 100 * exerciseCount).toInt()
        _exerciseCalories.value = calories
    }

    fun setUserName(name: String) {
        _userName.value = name
    }

    fun setKcalGoal(goal: Int) {
        _kcalGoal.value = goal
    }
}
