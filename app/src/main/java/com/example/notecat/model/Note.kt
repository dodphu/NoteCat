package com.example.notecat.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val title: String,
    val content: String,
    val date: String?
) : Serializable {
   // constructor(title: String, content: String, date: String?) : this(null, title, content, date)
   companion object {
       fun createNote(title: String, content: String, date: String?): Note {
           return Note(null, title, content, date)
       }
   }
}
