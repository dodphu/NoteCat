package com.example.notecat.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notecat.R
import com.example.notecat.activities.AddNoteActivity
import com.example.notecat.activities.EditNoteActivity
import com.example.notecat.adapter.AdapterNote
import com.example.notecat.databinding.FragmentAllNoteBinding
import com.example.notecat.model.Note
import com.example.notecat.viewmodel.NoteViewModel

class AllNoteFragment : Fragment(R.layout.fragment_all_note) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAllNoteBinding.bind(view)

        val edtSearchNote = binding.editTextSearch
        val rycv_note = binding.rycvMynote
        val imgv_notask = binding.imgvNoTask
        val txt_notask = binding.txtNoTask
        val noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        val adapterNote = AdapterNote(object : AdapterNote.onItemClickListenerFrag {
            override fun onItemClick(note: Note) {
                val intent = Intent(context,EditNoteActivity::class.java)
                intent.putExtra("note_data", note)
                context?.startActivity(intent)
            }
        })
        rycv_note.adapter = adapterNote
        //rycv_note.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rycv_note.layoutManager = LinearLayoutManager(context)

        noteViewModel.getAllNotesVM().observe(viewLifecycleOwner) { listNotes ->
            adapterNote.updateNotes(listNotes)
            hideShowEmpytyNote(listNotes, imgv_notask, txt_notask)
        }

        val fab = binding.fabAdd
        fab.setOnClickListener {
            //noteViewModel.addNoteVM(Note("phuu", "cccc", "phuu"))
            //noteViewModel.addNoteVM(Note.createNote("phu3", "adu", "22-12 -2222"))
            val intent = Intent(requireContext(), AddNoteActivity::class.java)
            startActivity(intent)
        }

        edtSearchNote.addTextChangedListener {
            noteViewModel.searchNoteVM("%${it?.trim()}%").observe(viewLifecycleOwner) { listNotes ->
                adapterNote.updateNotes(listNotes)
                hideShowEmpytyNote(listNotes, imgv_notask, txt_notask)
            }
        }

    }

    fun hideShowEmpytyNote(list: List<Note>, imgnotast: ImageView, txtnotask: TextView) {
        if (list.isEmpty()) {
            imgnotast.visibility = View.VISIBLE
            txtnotask.visibility = View.VISIBLE
        } else {
            imgnotast.visibility = View.GONE
            txtnotask.visibility = View.GONE
        }
    }

}