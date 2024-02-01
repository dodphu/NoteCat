package com.example.notecat.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
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

        activity?.let { activity ->
            val edtSearchNote = binding.editTextSearch
            val rycv_note = binding.rycvMynote
            val adapterNote = adapter_note()
            rycv_note.adapter = adapterNote
            rycv_note.layoutManager =
                StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)

            try {
                val noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
                noteViewModel.getAllNotesVM().observe(viewLifecycleOwner) { listNotes ->
                    adapterNote.updateData(listNotes)
                    //wordViewModel.insert(Word("Alpha", "FirstLetter"))
                    val note333 : Note = Note(3,"adu","aduu","add")
                    noteViewModel.addNoteVM(note333)
                }
            } catch (e: Exception) {
                Log.d("phu", "onViewCreated: $e")
            }
        }
    }
}