package com.example.notecat.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notecat.model.Note
import com.example.notecat.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val noteRepository = NoteRepository(application.applicationContext)
    fun getAllNotesVM(): LiveData<List<Note>> {
        return noteRepository.getAllNotesRepos()
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

    fun searchNoteVM(keySearch: String): LiveData<List<Note>> {
        return noteRepository.searchNoteRepos(keySearch)
    }
}