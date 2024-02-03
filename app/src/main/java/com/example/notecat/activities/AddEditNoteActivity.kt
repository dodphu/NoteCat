package com.example.notecat.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.notecat.R
import com.example.notecat.databinding.ActivityAddEditNoteBinding
import com.example.notecat.databinding.ActivityMainBinding
import com.example.notecat.model.Note
import com.example.notecat.viewmodel.NoteViewModel

class AddEditNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEditNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imgv_back = binding.imgBack
        val imgv_save = binding.imgSaveNote
        val edt_title_add = binding.edtTitleAdd.text
        val edt_content_add = binding.edtContentAdd.text

        val noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        imgv_back.setOnClickListener {
            finish()
        }
        imgv_save.setOnClickListener {
            noteViewModel.addNoteVM(
                Note.createNote("$edt_title_add", "$edt_content_add", "none")
            )
            finish()
        }

    }
}