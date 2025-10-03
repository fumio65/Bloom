package com.example.bloom.data.model

// Data class for our in-memory habit prototype
data class Habit(
    val id: Int,                  // unique id
    val title: String,            // habit name
    val description: String? = "",// optional description
    val frequency: Frequency,     // daily / weekly
    val isCompleted: Boolean = false, // track completion
    val streak: Int = 0           // simple streak counter
)

// Enum to represent frequency of a habit
enum class Frequency {
    DAILY,
    WEEKLY
}
