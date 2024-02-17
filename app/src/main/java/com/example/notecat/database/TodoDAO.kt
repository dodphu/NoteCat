package com.example.notecat.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.notecat.model.Todo

@Dao
interface TodoDAO {
    @Query("SELECT * FROM Todo ORDER BY idtodo DESC")
    fun getAllTodo(): LiveData<List<Todo>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertTodoDAO(todo: Todo)

    @Update
    suspend fun updateTodoDAO(todo: Todo)

    @Delete
    suspend fun deleteTodoDAO(todo: Todo)

}