package com.example.notecat.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notecat.model.Note
import com.example.notecat.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val noteRepository = NoteRepository(application.applicationContext)
    fun getAllNotesVM() {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.getAllNotesRepos()
        }
    }

    fun addNoteVM(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.addNoteRepos(note)
        }
    }

    fun updateNoteVM(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.updateNotesRepos(note)
        }
    }

    fun deleteNoteVM(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.deleteNoteRepos(note)
        }
    }

    fun searchNoteVM(keySearch: String) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.searchNoteRepos(keySearch)
        }
    }
}