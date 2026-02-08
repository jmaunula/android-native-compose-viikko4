package com.example.viikko4.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.viikko4.model.Task

@Composable
fun EditDialog(
    task: Task,
    onClose: () -> Unit,
    onUpdate: (Task) -> Unit,
    onDelete: (Task) -> Unit
) {
    // Tilamuuttujat kentille
    var title by remember { mutableStateOf(task.title) }
    var description by remember { mutableStateOf(task.description) }
    var dueDate by remember { mutableStateOf(task.dueDate) }
    var priority by remember { mutableStateOf(task.priority.toString()) }

    AlertDialog(
        onDismissRequest = onClose,
        title = {
            // otsikko + Delete-ikoni oikeassa yläkulmassa
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Edit Task", modifier = Modifier.weight(1f))
                IconButton(onClick = {
                    onDelete(task) // poistaa tehtävän
                    onClose() // sulkee dialogin
                }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Task")
                }
            }
        },
        text = {
            Column {
                // Muokkauskentät
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
            Button(onClick = {
                onUpdate(
                    task.copy(
                        title = title,
                        description = description,
                        dueDate = dueDate,
                        priority = priority.toIntOrNull() ?: task.priority
                    )
                )
            }) {
                Text("Save") // tallentaa muutokset
            }
        },
        dismissButton = {
            Button(onClick = onClose) {
                Text("Cancel") // sulkee dialogin ilman muutoksia
            }
        }
    )
}
