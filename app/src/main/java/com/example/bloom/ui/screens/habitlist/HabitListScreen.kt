package com.example.bloom.ui.screens.habitlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bloom.data.model.Habit

@Composable
fun HabitListScreen(
    habits: List<Habit>,
    onHabitCheckedChange: (Habit, Boolean) -> Unit,
    onHabitClick: (Habit) -> Unit,
    onHabitDelete: (Habit) -> Unit, // ✅ new callback
    modifier: Modifier = Modifier
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
                onCheckedChange = { isChecked -> onHabitCheckedChange(habit, isChecked) },
                onClick = { onHabitClick(habit) },
                onDelete = { onHabitDelete(habit) } // ✅ pass delete handler
            )
        }
    }
}

@Composable
fun HabitItem(
    habit: Habit,
    onCheckedChange: (Boolean) -> Unit,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Surface(
        tonalElevation = 2.dp,
        shadowElevation = 2.dp,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = habit.title, style = MaterialTheme.typography.titleMedium)
                habit.description?.let {
                    Text(text = it, style = MaterialTheme.typography.bodyMedium)
                }
                Text(
                    text = "Frequency: ${habit.frequency}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Right section (checkbox + delete)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Checkbox(
                    checked = habit.isCompleted,
                    onCheckedChange = { onCheckedChange(it) }
                )

                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Habit",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
