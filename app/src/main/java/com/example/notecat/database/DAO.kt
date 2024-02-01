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
interface DAO {
    @Query("SELECT * FROM Note ORDER BY id DESC")
    suspend fun getAllNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addNote(note: Note)

    @Update()
    suspend fun updateNotes(note: Note)

    @Delete()
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM note WHERE title LIKE :keySearch OR content LIKE :keySearch OR DATE LIKE :keySearch")
    suspend fun searchNotes(keySearch: String)
}