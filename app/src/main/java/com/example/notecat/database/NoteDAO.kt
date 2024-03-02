package com.example.notecat.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.notecat.model.Note



@Dao
interface NoteDAO {
    @Query("SELECT * FROM Note ORDER BY date DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addNote(note: Note)

    @Update()
    suspend fun updateNotes(note: Note)

    @Delete()
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM note WHERE title LIKE :keySearch OR content LIKE :keySearch OR date LIKE :keySearch ORDER BY date DESC")
    fun searchNotes(keySearch: String): LiveData<List<Note>>


}