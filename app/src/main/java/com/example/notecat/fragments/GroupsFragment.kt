package com.example.notecat.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notecat.R
import com.example.notecat.activities.EditNoteActivity
import com.example.notecat.adapter.AdapterNote
import com.example.notecat.databinding.FragmentGroupsBinding
import com.example.notecat.model.Note
import com.example.notecat.viewmodel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class GroupsFragment : Fragment(R.layout.fragment_groups) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentGroupsBinding.bind(view)
        val calendarv = binding.calendarView
        val txt_count = binding.txtDemghichu
        val rv_calendar = binding.rvCalendar


        val noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        val adapterNote = AdapterNote(object : AdapterNote.onItemClickListenerFrag {
            override fun onItemClick(note: Note) {
                val intent = Intent(context, EditNoteActivity::class.java)
                intent.putExtra("note_data", note)
                context?.startActivity(intent)
            }
        })
        rv_calendar.adapter = adapterNote
        //rycv_note.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rv_calendar.layoutManager = LinearLayoutManager(context)

        //hien thi date ngay hien tai
        val currentDate = Calendar.getInstance()
        val selectedDateString =
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(currentDate.time)
        noteViewModel.searchNoteVM("%${selectedDateString}%")
            .observe(viewLifecycleOwner) { listNotes ->
                adapterNote.updateNotes(listNotes)
            }

        calendarv.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)
            // Lấy ngày được chọn
            val selectedDateString =
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedDate.time)
            //  Log.d("phu", "onViewCreated: $selectedDateString")
            noteViewModel.searchNoteVM("%${selectedDateString}%")
                .observe(viewLifecycleOwner) { listNotes ->
                    adapterNote.updateNotes(listNotes)
                    if (listNotes.isEmpty()) {
                        txt_count.text = "Không có ghi chú !"
                    } else {
                        txt_count.text = "Có ${listNotes.size} ghi chú !"
                    }
                }
        }
    }
}