package com.example.viikko4.viewmodel

import androidx.lifecycle.ViewModel
import com.example.viikko4.model.Task
import com.example.viikko4.model.todoItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TaskViewModel : ViewModel() {

    // Lista tehtävistä, StateFlow jotta UI reagoi muutoksiin
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    // Valittu tehtävä, jota muokataan
    private val _selectedTask = MutableStateFlow<Task?>(null)
    val selectedTask: StateFlow<Task?> = _selectedTask

    // Näytetäänkö lisäysdialogi
    val addTaskDialogVisible = MutableStateFlow<Boolean>(false)

    // Automaattinen id tehtäville
    private var nextId = 0

    init {
        _tasks.value = todoItems // alustetaan olemassa olevilla tehtävillä
        nextId = todoItems.size + 1
    }

    // Avaa tehtävän muokkausta varten
    fun openTask(id: Int) {
        val task = _tasks.value.find { it.id == id }
        _selectedTask.value = task
    }

    // Lisää uusi tehtävä listaan
    fun addTask(task: Task) {
        val taskWithId = task.copy(id = nextId++) // annetaan automaattinen id
        _tasks.value += taskWithId
        addTaskDialogVisible.value = false // suljetaan dialogi
    }

    // Päivittää olemassa olevan tehtävän
    fun updateTask(updatedTask: Task) {
        _tasks.value = _tasks.value.map { task ->
            if (task.id == updatedTask.id) updatedTask
            else task
        }
        _selectedTask.value = null // suljetaan muokkausdialogi
    }

    // Vaihtaa tehtävän valmiustilan
    fun toggleDone(id: Int) {
        _tasks.value = _tasks.value.map { task ->
            if (task.id == id) task.copy(done = !task.done)
            else task
        }
    }

    // Poistaa tehtävän id:n perusteella
    fun removeTask(id: Int) {
        _tasks.value = _tasks.value.filter { it.id != id }
    }

    // Suodattaa tehtävät valmiuden mukaan
    fun filterByDone(done: Boolean) {
        _tasks.value = _tasks.value.filter { it.done == done }
    }

    // Järjestää tehtävät eräpäivän mukaan
    fun sortByDueDate() {
        _tasks.value = _tasks.value.sortedBy { it.dueDate }
    }

    // Sulkee muokkausdialogin
    fun closeDialog() {
        _selectedTask.value = null
    }
}
