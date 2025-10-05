package com.example.bloom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument // ✅ missing import fixed
import com.example.bloom.navigation.NavRoutes
import com.example.bloom.ui.screens.addhabit.AddHabitScreen
import com.example.bloom.ui.screens.edithabit.EditHabitScreen
import com.example.bloom.ui.screens.habitlist.HabitListScreen
import com.example.bloom.ui.screens.splash.SplashScreen
import com.example.bloom.ui.theme.BloomTheme
import com.example.bloom.viewmodel.HabitViewModel

class MainActivity : ComponentActivity() {
    private val habitViewModel: HabitViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BloomTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BloomNavHost(viewModel = habitViewModel)
                }
            }
        }
    }
}

@Composable
fun BloomNavHost(viewModel: HabitViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Splash.route
    ) {
        // 🪴 Splash Screen
        composable(NavRoutes.Splash.route) {
            SplashScreen {
                navController.navigate(NavRoutes.HabitList.route) {
                    popUpTo(NavRoutes.Splash.route) { inclusive = true }
                }
            }
        }

        // 📋 Habit List Screen
        composable(NavRoutes.HabitList.route) {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { navController.navigate(NavRoutes.AddHabit.route) },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ) {
                        Text("+", style = MaterialTheme.typography.titleLarge)
                    }
                }
            ) { padding ->
                HabitListScreen(
                    habits = viewModel.habits,
                    onHabitCheckedChange = { habit, isChecked ->
                        viewModel.toggleHabitCompletion(habit, isChecked)
                    },
                    onHabitClick = { habit ->
                        navController.navigate(NavRoutes.EditHabit.createRoute(habit.id))
                    },
                    onHabitDelete = { habit ->
                        viewModel.deleteHabit(habit)
                    },
                    modifier = Modifier.padding(padding) // ✅ fixed Scaffold padding
                )
            }
        }

        // ➕ Add Habit Screen
        composable(NavRoutes.AddHabit.route) {
            AddHabitScreen(
                onHabitAdded = { newHabit ->
                    viewModel.addHabit(newHabit)
                    navController.popBackStack()
                },
                onCancel = { navController.popBackStack() }
            )
        }

        // ✏️ Edit Habit Screen
        composable(
            route = NavRoutes.EditHabit.route,
            arguments = listOf(
                navArgument("habitId") { type = NavType.IntType } // ✅ missing type fixed
            )
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
