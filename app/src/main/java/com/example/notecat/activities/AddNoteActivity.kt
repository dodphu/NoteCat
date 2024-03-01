package com.example.notecat.activities

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notecat.R
import com.example.notecat.adapter.AdapterChangeColor
import com.example.notecat.adapter.OnItemClickListenerrr
import com.example.notecat.databinding.ActivityAddNoteBinding
import com.example.notecat.model.Note
import com.example.notecat.viewmodel.NoteViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class AddNoteActivity : AppCompatActivity(),OnItemClickListenerrr {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var view_newnote: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imgv_back = binding.imgBack
        val imgv_save = binding.imgSaveNote
        val edt_title_add = binding.edtTitleAdd.text
        val edt_content_add = binding.edtContentAdd.text
        val img_changecolor = binding.imgChangeColor
        view_newnote = binding.viewNewnotes

        val noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        imgv_back.setOnClickListener {
            finish()
        }
        imgv_save.setOnClickListener {
            if (edt_content_add.isEmpty() && edt_title_add.isEmpty()) {

            } else {
                val backgroundDrawable = view_newnote.background
                var backgroundColor = Color.WHITE
                if (backgroundDrawable is ColorDrawable) {
                    backgroundColor = backgroundDrawable.color
                }
                noteViewModel.addNoteVM(
                    Note(
                        null,
                        "$edt_title_add",
                        "$edt_content_add",
                        Note.getCurrentDateTime(),
                        backgroundColor
                    )
                )
            }
            finish()
        }

        img_changecolor.setOnClickListener {
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
        rvdialog.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL)
        //rvdialog.layoutManager = LinearLayoutManager(this)
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(dialogView)
        bottomSheetDialog.show()
    }

    override fun onItemClick(color: Int) {
        view_newnote.setBackgroundColor(color)
    }
}