package com.example.notecat.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notecat.model.Todo

@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDAO(): TodoDAO

    companion object {
        private var INSTANCE: TodoDatabase? = null

        fun getINSTANCE(context: Context): TodoDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        TodoDatabase::class.java,
                        "todo.db"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}