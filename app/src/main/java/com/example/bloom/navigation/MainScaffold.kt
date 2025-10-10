package com.example.bloom.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bloom.ui.screens.habitlist.HabitListScreen
import com.example.bloom.ui.screens.profile.ProfileScreen
import com.example.bloom.ui.screens.settings.SettingsScreen
import com.example.bloom.viewmodel.HabitViewModel

@Composable
fun MainScaffold(
    navController: NavHostController,
    viewModel: HabitViewModel,
    isDarkMode: Boolean,
    onThemeToggle: (Boolean) -> Unit
) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route.orEmpty()

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
            if (navController.currentDestination?.route == BottomNavRoutes.Habits.route) {
                FloatingActionButton(onClick = {
                    runCatching { navController.navigate(NavRoutes.AddHabit.route) }
                }) { Text("+") }
            }
        }
    ) { padding ->
        // 🌿 Show content based on current route
        val currentRoute = navController.currentDestination?.route
        when (currentRoute) {
            BottomNavRoutes.Habits.route -> {
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
            BottomNavRoutes.Profile.route -> {
                ProfileScreen(modifier = Modifier.padding(padding))
            }
            BottomNavRoutes.Settings.route -> {
                SettingsScreen(
                    isDarkMode = isDarkMode,
                    onThemeToggle = onThemeToggle,
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}
