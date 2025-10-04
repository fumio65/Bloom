package com.example.bloom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.example.bloom.data.model.Habit
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
                    AppEntry(habitViewModel)
                }
            }
        }
    }
}

@Composable
fun AppEntry(viewModel: HabitViewModel) {
    var showSplash by rememberSaveable { mutableStateOf(true) }
    var showAddHabit by rememberSaveable { mutableStateOf(false) }
    var habitToEdit by rememberSaveable { mutableStateOf<Habit?>(null) }

    if (showSplash) {
        SplashScreen { showSplash = false }
    } else {
        when {
            showAddHabit -> {
                AddHabitScreen(
                    onHabitAdded = { newHabit ->
                        viewModel.addHabit(newHabit)
                        showAddHabit = false
                    },
                    onCancel = { showAddHabit = false }
                )
            }

            habitToEdit != null -> {
                EditHabitScreen(
                    habit = habitToEdit!!,
                    onHabitUpdated = { updatedHabit ->
                        viewModel.updateHabit(updatedHabit)
                        habitToEdit = null
                    },
                    onCancel = { habitToEdit = null }
                )
            }

            else -> {
                Scaffold(
                    floatingActionButton = {
                        FloatingActionButton(onClick = { showAddHabit = true }) {
                            Text("+")
                        }
                    }
                ) { paddingValues ->
                    HabitListScreen(
                        habits = viewModel.habits,
                        onHabitCheckedChange = { habit, isChecked ->
                            viewModel.toggleHabitCompletion(habit, isChecked)
                        }
                        // ⬇️ Add click handling when HabitListScreen supports it
                        // onHabitClick = { habit -> habitToEdit = habit }
                    )
                }
            }
        }
    }
}
