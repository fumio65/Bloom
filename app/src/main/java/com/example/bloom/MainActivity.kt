package com.example.bloom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.bloom.navigation.BloomNavHost
import com.example.bloom.ui.theme.BloomTheme
import com.example.bloom.viewmodel.HabitViewModel

class MainActivity : ComponentActivity() {

    private val habitViewModel: HabitViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkMode by rememberSaveable { mutableStateOf(false) }

            BloomTheme(darkTheme = isDarkMode) {
                Surface(color = MaterialTheme.colorScheme.background) {
                    BloomNavHost(
                        viewModel = habitViewModel,
                        isDarkMode = isDarkMode,
                        onThemeToggle = { isDarkMode = it }
                    )
                }
            }
        }
    }
}
