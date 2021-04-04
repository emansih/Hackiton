package Hackiton.models

data class TodoItems(
        val userId: String = "",
        val todoItemId: String = "",
        val todoDescription: String = "",
        val todoDate: String = "",
        val isDone: Boolean = false
)