package com.example.notecat.repository

import android.content.Context
import com.example.notecat.database.NoteDatabase
import com.example.notecat.database.TodoDatabase
import com.example.notecat.model.Note
import com.example.notecat.model.Todo

class TodoRepository(context: Context) {
    private val tododb = TodoDatabase.getINSTANCE(context)
    private val tododao = tododb.todoDAO()
    fun getAllTodoRepos() = tododao.getAllTodo()
    suspend fun addTodoRepos(todo: Todo) = tododao.insertTodoDAO(todo)
    suspend fun updateTodoRepos(todo: Todo) = tododao.updateTodoDAO(todo)
    suspend fun deleteTodoRepos(todo: Todo) = tododao.deleteTodoDAO(todo)
}