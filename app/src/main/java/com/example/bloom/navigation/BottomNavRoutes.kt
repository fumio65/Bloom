package com.example.bloom.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.bloom.R

sealed class BottomNavRoutes(
    val route: String,
    @DrawableRes val icon: Int,
    @StringRes val label: Int
) {
    data object Habits : BottomNavRoutes(
        route = "habit_list",
        icon = R.drawable.ic_habit,
        label = R.string.nav_habits
    )

    data object Profile : BottomNavRoutes(
        route = "profile",
        icon = R.drawable.ic_profile,
        label = R.string.nav_profile
    )

    data object Settings : BottomNavRoutes(
        route = "settings",
        icon = R.drawable.ic_settings,
        label = R.string.nav_settings
    )

    companion object {
        val items = listOf(Habits, Profile, Settings)
    }
}
