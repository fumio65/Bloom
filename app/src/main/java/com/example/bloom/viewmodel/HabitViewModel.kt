package com.example.bloom.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.bloom.data.model.Frequency
import com.example.bloom.data.model.Habit

class HabitViewModel : ViewModel() {
    // backing state list for habits
    private val _habits = mutableStateListOf<Habit>()
    val habits: List<Habit> get() = _habits

    init {
        // preload some sample habits (temporary, for prototype)
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

    fun addHabit(habit: Habit) {
        _habits.add(habit)
    }

    fun deleteHabit(habit: Habit) {
        _habits.remove(habit)
    }

    fun updateHabit(updatedHabit: Habit) {
        val index = _habits.indexOfFirst { it.id == updatedHabit.id }
        if (index != -1) {
            _habits[index] = updatedHabit
        }
    }

}
