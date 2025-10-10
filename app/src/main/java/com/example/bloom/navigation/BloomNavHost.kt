package com.example.bloom.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.bloom.ui.screens.addhabit.AddHabitScreen
import com.example.bloom.ui.screens.edithabit.EditHabitScreen
import com.example.bloom.ui.screens.splash.SplashScreen
import com.example.bloom.viewmodel.HabitViewModel

@Composable
fun BloomNavHost(
    viewModel: HabitViewModel,
    isDarkMode: Boolean,
    onThemeToggle: (Boolean) -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Splash.route
    ) {
        // 🌱 Splash Screen
        composable(NavRoutes.Splash.route) {
            SplashScreen {
                // ✅ Navigate to the main graph safely
                navController.navigate("main") {
                    popUpTo(NavRoutes.Splash.route) { inclusive = true }
                }
            }
        }

        // 🌿 Main graph with bottom navigation
        navigation(
            startDestination = BottomNavRoutes.Habits.route,
            route = "main"
        ) {
            composable(BottomNavRoutes.Habits.route) {
                MainScaffold(
                    navController = navController,
                    viewModel = viewModel,
                    isDarkMode = isDarkMode,
                    onThemeToggle = onThemeToggle
                )
            }
            composable(BottomNavRoutes.Profile.route) {
                MainScaffold(
                    navController = navController,
                    viewModel = viewModel,
                    isDarkMode = isDarkMode,
                    onThemeToggle = onThemeToggle
                )
            }
            composable(BottomNavRoutes.Settings.route) {
                MainScaffold(
                    navController = navController,
                    viewModel = viewModel,
                    isDarkMode = isDarkMode,
                    onThemeToggle = onThemeToggle
                )
            }
        }

        // ➕ Add Habit
        composable(NavRoutes.AddHabit.route) {
            AddHabitScreen(
                onHabitAdded = { newHabit ->
                    viewModel.addHabit(newHabit)
                    navController.popBackStack()
                },
                onCancel = { navController.popBackStack() }
            )
        }

        // ✏️ Edit Habit
        composable(
            route = NavRoutes.EditHabit.route,
            arguments = listOf(navArgument("habitId") { type = NavType.IntType })
        ) { backStackEntry ->
            val habitId = backStackEntry.arguments?.getInt("habitId")
            val habit = viewModel.habits.find { it.id == habitId }
            habit?.let {
                EditHabitScreen(
                    habit = it,
                    onHabitUpdated = { updatedHabit ->
                        viewModel.updateHabit(updatedHabit)
                        navController.popBackStack()
                    },
                    onCancel = { navController.popBackStack() }
                )
            }
        }
    }
}
