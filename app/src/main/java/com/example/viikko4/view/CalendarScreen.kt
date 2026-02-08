package com.example.viikko4.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viikko4.model.Task
import com.example.viikko4.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    viewModel: TaskViewModel,
    onTaskClick: (Int) -> Unit = {},
    onNavigateHome: () -> Unit
) {
    val tasks by viewModel.tasks.collectAsState()
    val selectedTask by viewModel.selectedTask.collectAsState()

    // Ryhmitellään tehtävät päivämäärän mukaan
    val grouped = tasks.groupBy { it.dueDate ?: "No date" }

    Column(modifier = Modifier.padding(16.dp)) {

        // Yläpalkki takaisin-napilla
        TopAppBar(
            title = { Text("Calendar") },
            navigationIcon = {
                IconButton(onClick = onNavigateHome) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Go back")
                }
            }
        )

        // Lista päivämääristä ja tehtävistä
        LazyColumn {
            grouped.forEach { (date, tasksOfDay) ->
                item {
                    Text(
                        text = date,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )
                }

                items(tasksOfDay) { task ->
                    CalendarTaskCard(task = task, onTaskClick = onTaskClick)
                }
            }
        }
    }

    // Muokkausdialogi
    if (selectedTask != null) {
        EditDialog(
            task = selectedTask!!,
            onClose = { viewModel.closeDialog() },
            onUpdate = { viewModel.updateTask(it) },
            onDelete = { task -> viewModel.removeTask(task.id) }
        )
    }
}

@Composable
fun CalendarTaskCard(
    task: Task,
    onTaskClick: (Int) -> Unit
) {
    // Yksittäinen tehtävä kortti kalenterissa
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .clickable { onTaskClick(task.id) }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(task.title, style = MaterialTheme.typography.titleMedium)
            if (task.description.isNotBlank()) {
                Text(task.description, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
