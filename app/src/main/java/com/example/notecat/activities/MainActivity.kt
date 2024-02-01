package com.example.notecat.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notecat.R
import com.example.notecat.viewmodel.NoteViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}