package com.example.notecat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notecat.R

interface OnItemClickListenerrr {
    fun onItemClick(color: Int)
}

class AdapterChangeColor(
    private val colors: List<Int>,
    private val itemClickListener: OnItemClickListenerrr
) : RecyclerView.Adapter<AdapterChangeColor.ViewHolder>() {

    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        private val colorCircle: View = itemView.findViewById(R.id.colorCircle)

        fun bind(color: Int) {
            colorCircle.setBackgroundColor(color)
            itemView.setOnClickListener {
                itemClickListener.onItemClick(color)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterChangeColor.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.color_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterChangeColor.ViewHolder, position: Int) {
        val color = colors[position]
        holder.bind(color)
    }

    override fun getItemCount(): Int {
        return colors.size
    }
}
