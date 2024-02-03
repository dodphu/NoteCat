package com.example.notecat.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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
       fun getCurrentDateTime(): String {
           val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
           val currentDate = Calendar.getInstance().time
           return dateFormat.format(currentDate)
       }
   }
}
