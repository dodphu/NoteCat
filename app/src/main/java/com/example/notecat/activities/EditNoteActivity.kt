package com.example.notecat.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

        val imgv_delete = binding.imgDeletess
        val imgv_edit = binding.imgOkEdit
        val edt_title_edt = binding.edtTitleEdit
        val edt_content_edt = binding.edtContentEdit

        val note = intent.getSerializableExtra("note_data") as? Note
        if (note != null) {
            edt_title_edt.setText(note.title)
            edt_content_edt.setText(note.content)
        }
        val noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        imgv_delete.setOnClickListener {
            if (note != null) {
                AlertDialog.Builder(this)
                    .setTitle("XÓA GHI CHÚ")
                    .setMessage("Bạn có muốn xóa?")
                    .setPositiveButton("Xóa") { _, _ ->
                        noteViewModel.deleteNoteVM(note)
                        Toast.makeText(this, "Xóa thành công !", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .setNegativeButton("Hủy") { _, _ ->
                        finish()
                    }
                    .show()
            }
        }
        imgv_edit.setOnClickListener {
            val newTitle = edt_title_edt.text
            val newContent = edt_content_edt.text
            if (note != null) {
                note.title = newTitle.toString()
                note.content = newContent.toString()
                noteViewModel.updateNoteVM(note)
                Toast.makeText(this, "Đã update !", Toast.LENGTH_SHORT).show()
            }

            finish()
        }

    }
}