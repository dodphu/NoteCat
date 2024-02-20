package com.example.notecat.fragments

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notecat.R
import com.example.notecat.activities.AddNoteActivity
import com.example.notecat.activities.EditNoteActivity
import com.example.notecat.adapter.AdapterNote
import com.example.notecat.databinding.FragmentAllNoteBinding
import com.example.notecat.model.Note
import com.example.notecat.viewmodel.NoteViewModel
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

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
                val intent = Intent(context, EditNoteActivity::class.java)
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
            val intent = Intent(requireContext(), AddNoteActivity::class.java)
            startActivity(intent)
        }

        edtSearchNote.addTextChangedListener {
            noteViewModel.searchNoteVM("%${it?.trim()}%").observe(viewLifecycleOwner) { listNotes ->
                adapterNote.updateNotes(listNotes)
                hideShowEmpytyNote(listNotes, imgv_notask, txt_notask)
            }

        }

        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val swipePosition = viewHolder.bindingAdapterPosition
                val swipeItem = adapterNote.getItem(swipePosition)
                noteViewModel.deleteNoteVM(swipeItem)
                val snackbar = Snackbar.make(
                    rycv_note,
                    "Đã xóa ghi chú !",
                    Snackbar.LENGTH_LONG
                )
                snackbar.setAction("Hoàn tác") {
                    noteViewModel.addNoteVM(swipeItem)
                    rycv_note.scrollToPosition(swipePosition)
                }
                snackbar.show()
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                val decorator = RecyclerViewSwipeDecorator.Builder(
                    requireContext(),
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )

                decorator.addSwipeLeftLabel("Xóa")
                    .addSwipeLeftActionIcon(R.drawable.baseline_clear_24_white)
                    .addSwipeLeftBackgroundColor(Color.RED)
                    .addSwipeRightLabel("Tạo todo")
                    .setSwipeLeftLabelColor(Color.WHITE)
                    .addSwipeRightActionIcon(R.drawable.checklist)
                    .addSwipeRightBackgroundColor(Color.GREEN)
                    .setSwipeRightLabelColor(Color.WHITE)
                    .create()
                    .decorate()
            }
        })
        itemTouchHelper.attachToRecyclerView(rycv_note)
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