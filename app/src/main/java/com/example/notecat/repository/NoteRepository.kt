package com.example.notecat.repository

import android.content.Context
import com.example.notecat.database.NoteDatabase
import com.example.notecat.model.Note

class NoteRepository(context: Context) {
    private val notedb = NoteDatabase.getInstance(context)
    private val noteDao = notedb.noteDao()
    suspend fun getAllNotesRepos() = noteDao.getAllNotes()
    suspend fun addNoteRepos(note: Note) = notedb.noteDao().addNote(note)
    suspend fun updateNotesRepos(note: Note) = notedb.noteDao().updateNotes(note)
    suspend fun deleteNoteRepos(note: Note) = notedb.noteDao().deleteNote(note)
    suspend fun searchNoteRepos(keysearch: String) = notedb.noteDao().searchNotes(keysearch)

}