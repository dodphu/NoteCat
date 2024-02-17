package com.example.notecat.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.Locale

@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val idtodo: Int? = null,
    val todoitem: String,
    var isChoose: Boolean
): Serializable
