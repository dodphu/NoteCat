package com.example.notecat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notecat.R
import com.example.notecat.databinding.NoteItemBinding
import com.example.notecat.model.Note

class adapter_note : RecyclerView.Adapter<adapter_note.NoteViewHolder>() {
    private var list_notes: List<Note> = ArrayList()

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = NoteItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapter_note.NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: adapter_note.NoteViewHolder, position: Int) {
        holder.binding.titleItem.text = list_notes[position].title
        holder.binding.contentItem.text = list_notes[position].content
    }

    override fun getItemCount(): Int {
        return list_notes.size
    }
    fun updateData(newNoteList: List<Note>) {
        list_notes = newNoteList
        notifyDataSetChanged()
    }
}