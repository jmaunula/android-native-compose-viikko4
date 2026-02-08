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
fun DetailScreen(task: Task, onClose: () -> Unit, onUpdate: (Task) -> Unit){
    var title by remember { mutableStateOf(task.title) }
    var description by remember { mutableStateOf(task.description) }

    AlertDialog(
        onDismissRequest = onClose,
        title = { Text("Edit Task")},
        text = {
            Column {
                TextField(value = title, onValueChange = {title = it}, label = {Text("Title")})
                TextField(value = description, onValueChange = {description = it}, label = {Text("Description")})
            }
        },
        confirmButton = {
            Button(onClick = {
                onUpdate(task.copy(title = title, description = description))
            }) { Text("Save")}
        },
        dismissButton = {
            Button(onClick = onClose) {
                Text("Cancel") }
        }
    )
}