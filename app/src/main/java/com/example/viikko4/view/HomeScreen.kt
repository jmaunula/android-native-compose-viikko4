package com.example.viikko4.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
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
import com.example.viikko4.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: TaskViewModel,
    onTaskClick: (Int) -> Unit = {},
    onAddClick: () -> Unit = {},
    onNavigateCalendar: () -> Unit = {},
    onNavigateSettings: () -> Unit = {}
) {
    // Haetaan flowsta lista tehtävistä ja valittu tehtävä
    val taskList by viewModel.tasks.collectAsState()
    val selectedTask by viewModel.selectedTask.collectAsState()
    val addTaskFlag by viewModel.addTaskDialogVisible.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {

        // Yläpalkki, jossa navigointi
        TopAppBar(
            title = { Text("Task List") },
            actions = {
                IconButton(onClick = onNavigateCalendar) {
                    Icon(Icons.Default.CalendarMonth, contentDescription = "Go to calendar")
                }
                IconButton(onClick = onNavigateSettings) {
                    Icon(Icons.Default.Settings, contentDescription = "Go to settings")
                }
            }
        )

        // Add Task -nappi
        Row {
            Button(onClick = onAddClick, modifier = Modifier.padding(8.dp)) {
                Text("Add Task")
            }
        }

        // Lista tehtävistä
        LazyColumn {
            items(taskList) { task ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onTaskClick(task.id) } // avaa tehtävän muokkausta
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(task.title, style = MaterialTheme.typography.headlineSmall)
                            Text(task.description)
                        }
                        Checkbox(
                            checked = task.done,
                            onCheckedChange = { viewModel.toggleDone(task.id) } // vaihtaa done-tilaa
                        )
                    }
                }
            }
        }
    }

    // Näytetään muokkausdialogi, jos tehtävä valittu
    if (selectedTask != null) {
        EditDialog(
            task = selectedTask!!,
            onClose = { viewModel.closeDialog() },
            onUpdate = { viewModel.updateTask(it) },
            onDelete = { task -> viewModel.removeTask(task.id) } // poistaa tehtävän id:n mukaan
        )
    }

    // Näytetään lisäysdialogi
    if (addTaskFlag) {
        AddDialog(
            onClose = { viewModel.addTaskDialogVisible.value = false },
            onSave = { viewModel.addTask(it) }
        )
    }
}
