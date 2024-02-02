package com.example.notecat.repository

import android.content.Context
import com.example.notecat.database.NoteDatabase
import com.example.notecat.model.Note

class NoteRepository(context: Context) {
    private val notedb = NoteDatabase.getInstance(context)
    private val noteDao = notedb.noteDao()
    fun getAllNotesRepos() = noteDao.getAllNotes()
    suspend fun addNoteRepos(note: Note) = noteDao.addNote(note)
    suspend fun updateNotesRepos(note: Note) = noteDao.updateNotes(note)
    suspend fun deleteNoteRepos(note: Note) = noteDao.deleteNote(note)
    fun searchNoteRepos(keysearch: String) = noteDao.searchNotes(keysearch)

}