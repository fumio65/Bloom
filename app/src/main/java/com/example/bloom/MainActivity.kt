package com.example.bloom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.bloom.navigation.BottomNavRoutes
import com.example.bloom.navigation.NavRoutes
import com.example.bloom.ui.screens.addhabit.AddHabitScreen
import com.example.bloom.ui.screens.edithabit.EditHabitScreen
import com.example.bloom.ui.screens.habitlist.HabitListScreen
import com.example.bloom.ui.screens.profile.ProfileScreen
import com.example.bloom.ui.screens.settings.SettingsScreen
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
        // 🌱 Splash Screen
        composable(NavRoutes.Splash.route) {
            SplashScreen {
                navController.navigate("main") {
                    popUpTo(NavRoutes.Splash.route) { inclusive = true }
                }
            }
        }

        // 🌿 Main Navigation Graph (Bottom Nav)
        navigation(startDestination = BottomNavRoutes.Habits.route, route = "main") {
            composable(BottomNavRoutes.Habits.route) {
                MainScaffold(navController = navController, viewModel = viewModel)
            }
            composable(BottomNavRoutes.Profile.route) {
                ProfileScreen()
            }
            composable(BottomNavRoutes.Settings.route) {
                SettingsScreen()
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
                navArgument("habitId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val habitId = backStackEntry.arguments?.getInt("habitId")
            val habit = viewModel.habits.find { it.id == habitId }

            if (habit != null) {
                EditHabitScreen(
                    habit = habit,
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

@Composable
fun MainScaffold(navController: NavHostController, viewModel: HabitViewModel) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route.orEmpty()

                // ✅ Iterate safely through nav items
                BottomNavRoutes.items.forEach { item ->
                    val route = item.route
                    NavigationBarItem(
                        selected = currentRoute == route,
                        onClick = {
                            if (route.isNotEmpty() && route != currentRoute) {
                                runCatching {
                                    navController.navigate(route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }.onFailure { it.printStackTrace() }
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = stringResource(id = item.label)
                            )
                        },
                        label = { Text(stringResource(id = item.label)) }
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    runCatching {
                        navController.navigate(NavRoutes.AddHabit.route)
                    }.onFailure { it.printStackTrace() }
                }
            ) {
                Text("+")
            }
        }
    ) { padding ->
        HabitListScreen(
            habits = viewModel.habits,
            onHabitCheckedChange = { habit, isChecked ->
                viewModel.toggleHabitCompletion(habit, isChecked)
            },
            onHabitClick = { habit ->
                runCatching {
                    navController.navigate(NavRoutes.EditHabit.createRoute(habit.id))
                }.onFailure { it.printStackTrace() }
            },
            onHabitDelete = { habit ->
                viewModel.deleteHabit(habit)
            },
            modifier = Modifier.padding(padding)
        )
    }
}
