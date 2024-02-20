package com.example.notecat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notecat.R
import com.example.notecat.databinding.NoteItemBinding
import com.example.notecat.model.Note

class AdapterNote(private val listener: onItemClickListenerFrag) :
    RecyclerView.Adapter<AdapterNote.NoteViewHolder>() {
    private var list_notes: MutableList<Note> = mutableListOf()

    interface onItemClickListenerFrag {
        fun onItemClick(note: Note)
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = NoteItemBinding.bind(itemView)
        val title = binding.titleItem
        val content = binding.contentItem
        val date = binding.dateItem
        val view_item = binding.noteViewItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterNote.NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterNote.NoteViewHolder, position: Int) {
        holder.title.text = list_notes[position].title
        holder.content.text = list_notes[position].content
        holder.date.text = list_notes[position].date
        list_notes[position].color?.let { holder.view_item.setBackgroundColor(it) }


        holder.itemView.setOnClickListener {
            listener.onItemClick(list_notes[position])
        }
    }

    override fun getItemCount(): Int {
        return list_notes.size
    }

    fun updateNotes(newNotes: List<Note>) {
        list_notes = newNotes.toMutableList()
        notifyDataSetChanged()
    }

    fun getItem(position: Int): Note {
        return list_notes[position]
    }


}