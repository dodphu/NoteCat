package com.example.notecat.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.notecat.R
import com.example.notecat.databinding.ActivityAddNoteBinding
import com.example.notecat.databinding.ActivityEditNoteBinding
import com.example.notecat.model.Note
import com.example.notecat.viewmodel.NoteViewModel

class EditNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imgv_cancel = binding.imgCancel
        val imgv_edit = binding.imgOkEdit
        val edt_title_edt = binding.edtTitleEdit
        val edt_content_edt = binding.edtContentEdit

        val note = intent.getSerializableExtra("note_data") as? Note
        if (note != null) {
            edt_title_edt.setText(note.title)
            edt_content_edt.setText(note.content)
        }
        val noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        imgv_cancel.setOnClickListener {
            if (note != null) {
                noteViewModel.deleteNoteVM(note)
            }
            finish()
        }
        imgv_edit.setOnClickListener {
            val newTitle = edt_title_edt.text
            val newContent = edt_content_edt.text
            if (note != null) {
                note.title = newTitle.toString()
            }
            if (note != null) {
                note.content = newContent.toString()
            }
            if (note != null) {
                noteViewModel.updateNoteVM(note)
            }

            finish()
        }

    }
}