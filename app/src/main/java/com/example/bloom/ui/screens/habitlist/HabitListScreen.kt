package com.example.bloom.ui.screens.habitlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bloom.data.model.Habit

@Composable
fun HabitListScreen(
    habits: List<Habit>,
    onHabitCheckedChange: (Habit, Boolean) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(habits) { habit ->
                HabitItem(
                    habit = habit,
                    onCheckedChange = { isChecked ->
                        onHabitCheckedChange(habit, isChecked)
                    }
                )
            }
        }
    }
}

@Composable
fun HabitItem(
    habit: Habit,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = habit.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            if (!habit.description.isNullOrEmpty()) {
                Text(
                    text = habit.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Checkbox(
            checked = habit.isCompleted,
            onCheckedChange = onCheckedChange
        )
    }
}
