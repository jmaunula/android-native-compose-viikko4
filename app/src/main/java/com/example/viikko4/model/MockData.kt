package com.example.viikko4.model

val todoItems = listOf<Task>(
    Task(
        id = 1,
        title = "Buy groceries",
        description = "Milk, bread and vegetables",
        priority = 2,
        dueDate = "2026-01-15",
        done = false
    ),
    Task(
        id = 2,
        title = "Finish Android assignment",
        description = "Complete domain and UI parts",
        priority = 1,
        dueDate = "2026-01-18",
        done = false
    ),
    Task(
        id = 3,
        title = "Walk the dog",
        description = "Evening walk around the block",
        priority = 3,
        dueDate = "2026-01-12",
        done = true
    ),
    Task(
        id = 4,
        title = "Read Kotlin documentation",
        description = "Focus on data classes and collections",
        priority = 2,
        dueDate = "2026-01-20",
        done = false
    ),
    Task(
        id = 5,
        title = "Clean the house",
        description = "Vacuum and clean the kitchen",
        priority = 4,
        dueDate = "2026-01-14",
        done = true
    ),
    Task(
        id = 6,
        title = "Prepare presentation",
        description = "Slides for next week's demo",
        priority = 1,
        dueDate = "2026-01-22",
        done = false
    )
)