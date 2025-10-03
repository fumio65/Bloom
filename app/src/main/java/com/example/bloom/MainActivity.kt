package com.example.bloom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.bloom.data.model.Frequency
import com.example.bloom.data.model.Habit
import com.example.bloom.ui.screens.habitlist.HabitListScreen
import com.example.bloom.ui.screens.splash.SplashScreen
import com.example.bloom.ui.theme.BloomTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BloomTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppEntry()
                }
            }
        }
    }
}

@Composable
fun AppEntry() {
    var showSplash by remember { mutableStateOf(true) }

    if (showSplash) {
        SplashScreen {
            showSplash = false
        }
    } else {
        // ✅ Show Habit List after splash
        val sampleHabits = remember {
            mutableStateListOf(
                Habit(1, "Drink Water", "8 glasses a day", Frequency.DAILY),
                Habit(2, "Morning Walk", "30 minutes", Frequency.DAILY),
                Habit(3, "Read a Book", "At least 10 pages", Frequency.WEEKLY)
            )
        }

        HabitListScreen(
            habits = sampleHabits,
            onHabitCheckedChange = { habit, isChecked ->
                val index = sampleHabits.indexOf(habit)
                if (index != -1) {
                    sampleHabits[index] = habit.copy(isCompleted = isChecked)
                }
            }
        )
    }
}
