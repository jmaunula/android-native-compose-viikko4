package com.example.viikko4.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.viikko4.model.Task

@Composable
fun AddDialog(
    onClose: () -> Unit,
    onSave: (Task) -> Unit
) {
    // Tekstikenttien tilamuuttujat
    var title by remember { mutableStateOf("New Task") }
    var description by remember { mutableStateOf("description") }
    var dueDate by remember { mutableStateOf("2026-03-01") }
    var priority by remember { mutableStateOf("1") }

    AlertDialog(
        onDismissRequest = onClose,
        title = { Text("Add Task") },
        text = {
            Column {
                // Kentät tehtävän tiedoille
                TextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") })
                TextField(
                    value = dueDate,
                    onValueChange = { dueDate = it },
                    label = { Text("Due Date") })
                TextField(
                    value = priority,
                    onValueChange = { priority = it },
                    label = { Text("Priority") })
            }
        },
        confirmButton = {
            // Save-nappi
            Button(onClick = {
                val newTask = Task(
                    id = 0, // id annetaan ViewModelissä
                    title = title,
                    description = description,
                    priority = priority.toIntOrNull() ?: 0,
                    dueDate = dueDate,
                    done = false
                )
                onSave(newTask) // lähetetään takaisin ViewModelille
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            // Cancel-nappi sulkee dialogin
            Button(onClick = onClose) {
                Text("Cancel")
            }
        }
    )
}
