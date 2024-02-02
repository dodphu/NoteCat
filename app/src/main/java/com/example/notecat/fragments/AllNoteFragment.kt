package com.example.notecat.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notecat.R
import com.example.notecat.adapter.adapter_note
import com.example.notecat.databinding.FragmentAllNoteBinding
import com.example.notecat.model.Note
import com.example.notecat.viewmodel.NoteViewModel
import kotlin.math.log

class AllNoteFragment : Fragment(R.layout.fragment_all_note) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAllNoteBinding.bind(view)

        val edtSearchNote = binding.editTextSearch
        val layout_notask = binding.layoutNotask
        val rycv_note = binding.rycvMynote
        val adapterNote = adapter_note()
        rycv_note.adapter = adapterNote
        rycv_note.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        val noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        noteViewModel.getAllNotesVM().observe(viewLifecycleOwner) { listNotes ->
            if (!listNotes.isNullOrEmpty()) {
                layout_notask.visibility = View.GONE
                adapterNote.updateNotes(listNotes)
                Log.d("phu", "onViewCreated: ${listNotes[0]}")
            } else {
                layout_notask.visibility = View.VISIBLE
            }
        }


        val fab = binding.fabAdd
        fab.setOnClickListener {
            //noteViewModel.addNoteVM(Note("phuu", "cccc", "phuu"))
            noteViewModel.addNoteVM(Note.createNote("phu3", "adu", "22-12 -2222"))
        }
    }
}