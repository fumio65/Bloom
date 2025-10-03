package com.example.bloom.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.bloom.data.model.Frequency
import com.example.bloom.data.model.Habit

class HabitViewModel : ViewModel() {
    // In-memory list of habits (rotation-safe)
    private val _habits = mutableStateListOf<Habit>()
    val habits: List<Habit> get() = _habits

    init {
        // Sample data
        _habits.addAll(
            listOf(
                Habit(1, "Drink Water", "8 glasses a day", Frequency.DAILY),
                Habit(2, "Morning Walk", "30 minutes", Frequency.DAILY),
                Habit(3, "Read a Book", "At least 10 pages", Frequency.WEEKLY)
            )
        )
    }

    fun toggleHabitCompletion(habit: Habit, isCompleted: Boolean) {
        val index = _habits.indexOf(habit)
        if (index != -1) {
            _habits[index] = habit.copy(isCompleted = isCompleted)
        }
    }
}
