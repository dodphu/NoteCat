package com.example.notecat.model

import android.graphics.Color
import android.graphics.drawable.Drawable
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
    var title: String,
    var content: String,
    val date: String?,
    var color: Int?
) : Serializable {
    companion object {
        fun getCurrentDateTime(): String {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy - HH:mm:ss", Locale.getDefault())
            val currentDate = Calendar.getInstance().time
            return dateFormat.format(currentDate)
        }
    }
}
