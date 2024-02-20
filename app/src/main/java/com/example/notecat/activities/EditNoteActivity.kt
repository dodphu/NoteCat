package com.example.notecat.activities

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notecat.R
import com.example.notecat.adapter.AdapterChangeColor
import com.example.notecat.adapter.OnItemClickListenerrr
import com.example.notecat.databinding.ActivityAddNoteBinding
import com.example.notecat.databinding.ActivityEditNoteBinding
import com.example.notecat.model.Note
import com.example.notecat.viewmodel.NoteViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class EditNoteActivity : AppCompatActivity(), OnItemClickListenerrr {
    private lateinit var binding: ActivityEditNoteBinding
    private lateinit var view_linear_edit: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imgv_delete = binding.imgDeletess
        val imgv_edit = binding.imgOkEdit
        val edt_title_edt = binding.edtTitleEdit
        val edt_content_edt = binding.edtContentEdit
        val imgv_changecolor = binding.imgChangeColorEdit
        view_linear_edit = binding.viewLinearEdit

        val note = intent.getSerializableExtra("note_data") as? Note
        if (note != null) {
            edt_title_edt.setText(note.title)
            edt_content_edt.setText(note.content)
            view_linear_edit.setBackgroundColor(note.color?.toInt()!!)
        }
        val noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        imgv_delete.setOnClickListener {
//            if (note != null) {
//                AlertDialog.Builder(this)
//                    .setTitle("XÓA GHI CHÚ")
//                    .setMessage("Bạn có muốn xóa?")
//                    .setPositiveButton("Xóa") { _, _ ->
//                        noteViewModel.deleteNoteVM(note)
//                        Toast.makeText(this, "Xóa thành công !", Toast.LENGTH_SHORT).show()
//                        finish()
//                    }
//                    .setNegativeButton("Hủy") { _, _ ->
//                        finish()
//                    }
//                    .show()
//            }
            finish()
        }
        imgv_edit.setOnClickListener {
            val newTitle = edt_title_edt.text
            val newContent = edt_content_edt.text
            val newbackgroundDrawable = view_linear_edit.background
            if (newbackgroundDrawable is ColorDrawable) {
                val newbackgroundColor = newbackgroundDrawable.color
                if (note != null) {
                    note.title = newTitle.toString()
                    note.content = newContent.toString()
                    note.color = newbackgroundColor
                    noteViewModel.updateNoteVM(note)
                    Toast.makeText(this, "Đã update !", Toast.LENGTH_SHORT).show()
                }
            }

            finish()
        }
        imgv_changecolor.setOnClickListener {
            showColorpickerDialog()
        }

    }

    private fun showColorpickerDialog() {
        val colorArray = resources.obtainTypedArray(R.array.color_picker)
        val colorList = mutableListOf<Int>()
        for (i in 0 until colorArray.length()) {
            colorList.add(colorArray.getColor(i, 0))
        }
        colorArray.recycle()

        val dialogView = LayoutInflater.from(this).inflate(R.layout.color_picker_custom, null)
        val colorPickerAdapter = AdapterChangeColor(colorList, this)
        val rvdialog = dialogView.findViewById<RecyclerView>(R.id.colorrecyclerview)
        rvdialog.adapter = colorPickerAdapter
        rvdialog.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)
        //rvdialog.layoutManager = LinearLayoutManager(this)
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(dialogView)
        bottomSheetDialog.show()
    }

    override fun onItemClick(newcolor: Int) {
        view_linear_edit.setBackgroundColor(newcolor)
    }
}