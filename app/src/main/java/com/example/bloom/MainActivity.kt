package com.example.bloom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
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

    if (showSplash) {
        SplashScreen {
            showSplash = false
        }
    } else {
        HabitListScreen(
            habits = viewModel.habits,
            onHabitCheckedChange = { habit, isChecked ->
                viewModel.toggleHabitCompletion(habit, isChecked)
            }
        )
    }
}
