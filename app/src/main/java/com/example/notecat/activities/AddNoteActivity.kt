package com.example.notecat.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.notecat.databinding.ActivityAddNoteBinding
import com.example.notecat.model.Note
import com.example.notecat.viewmodel.NoteViewModel

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
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
            if (edt_content_add.isEmpty() && edt_title_add.isEmpty()) {

            } else {
                noteViewModel.addNoteVM(
                    Note(null, "$edt_title_add", "$edt_content_add", Note.getCurrentDateTime())
                )
            }
            finish()
        }

    }
}