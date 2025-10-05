package com.example.bloom.navigation

sealed class NavRoutes(val route: String) {
    data object Splash : NavRoutes("splash")
    data object HabitList : NavRoutes("habit_list")
    data object AddHabit : NavRoutes("add_habit")
    data object EditHabit : NavRoutes("edit_habit/{habitId}") {
        fun createRoute(habitId: Int) = "edit_habit/$habitId"
    }
}
