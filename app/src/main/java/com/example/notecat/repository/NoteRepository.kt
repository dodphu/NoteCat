package com.example.notecat.repository

import android.content.Context
import com.example.notecat.database.NoteDatabase
import com.example.notecat.model.Note

class NoteRepository(context: Context) {
    private val notedb = NoteDatabase.getInstance(context)
    private val noteDao = notedb.noteDao()
    suspend fun get_All_Notes() = noteDao.getAllNotes()
    suspend fun add_Note(note: Note) = notedb.noteDao().addNote(note)
    suspend fun update_Notes(note: Note) = notedb.noteDao().updateNotes(note)
    suspend fun delete_Note(note: Note) = notedb.noteDao().deleteNote(note)
    suspend fun search_Note(keysearch: String) = notedb.noteDao().searchNotes(keysearch)

}