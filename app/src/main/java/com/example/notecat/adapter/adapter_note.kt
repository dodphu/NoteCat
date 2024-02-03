package com.example.notecat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notecat.R
import com.example.notecat.databinding.NoteItemBinding
import com.example.notecat.model.Note

class adapter_note : RecyclerView.Adapter<adapter_note.NoteViewHolder>() {
    private var list_notes: List<Note> = listOf()

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = NoteItemBinding.bind(itemView)
        val title = binding.titleItem
        val content = binding.contentItem
        val date = binding.dateItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapter_note.NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: adapter_note.NoteViewHolder, position: Int) {
        holder.title.text = list_notes[position].title
        holder.content.text = list_notes[position].content
        holder.date.text = list_notes[position].date

    }

    override fun getItemCount(): Int {
        return list_notes.size
    }

    fun updateNotes(newNotes: List<Note>) {
        list_notes = newNotes
        notifyDataSetChanged()
    }
}