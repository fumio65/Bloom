package com.example.bloom.ui.screens.addhabit

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bloom.data.model.Frequency
import com.example.bloom.data.model.Habit

@Composable
fun AddHabitScreen(
    onHabitAdded: (Habit) -> Unit,
    onCancel: () -> Unit
) {
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var frequency by rememberSaveable { mutableStateOf(Frequency.DAILY) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Add New Habit",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description (optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Text("Frequency", style = MaterialTheme.typography.titleMedium)

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            RadioButton(
                selected = frequency == Frequency.DAILY,
                onClick = { frequency = Frequency.DAILY }
            )
            Text("Daily")

            RadioButton(
                selected = frequency == Frequency.WEEKLY,
                onClick = { frequency = Frequency.WEEKLY }
            )
            Text("Weekly")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        val newHabit = Habit(
                            id = (0..100000).random(), // temporary ID generator
                            title = title,
                            description = description.ifBlank { null },
                            frequency = frequency
                        )
                        onHabitAdded(newHabit)
                    }
                }
            ) {
                Text("Save")
            }

            OutlinedButton(onClick = onCancel) {
                Text("Cancel")
            }
        }
    }
}
