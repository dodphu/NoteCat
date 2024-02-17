package com.example.notecat.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notecat.model.Note
import com.example.notecat.model.Todo
import com.example.notecat.repository.NoteRepository
import com.example.notecat.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val todoRepository = TodoRepository(application.applicationContext)
    fun getAllTodoVM(): LiveData<List<Todo>> {
        return todoRepository.getAllTodoRepos()
    }

    fun addTodoVM(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            todoRepository.addTodoRepos(todo)
        }
    }

    fun updateTodoVM(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            todoRepository.updateTodoRepos(todo)
        }
    }

    fun deleteTodoVM(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            todoRepository.deleteTodoRepos(todo)
        }
    }

}