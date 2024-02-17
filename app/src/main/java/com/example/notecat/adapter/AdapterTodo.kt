package com.example.notecat.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notecat.R
import com.example.notecat.databinding.TodoItemBinding
import com.example.notecat.model.Todo
import com.example.notecat.service.TodoService

class AdapterTodo(private val context: Context) :
    RecyclerView.Adapter<AdapterTodo.TodoViewHolder>() {
    private var todo_list: List<Todo> = listOf()

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = TodoItemBinding.bind(itemView)
        val cbx_todo = binding.cbxTodo
        val imgv_todo = binding.imgvNoti
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterTodo.TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterTodo.TodoViewHolder, position: Int) {
        holder.cbx_todo.text = todo_list[position].todoitem
        holder.cbx_todo.isChecked = todo_list[position].isChoose
        holder.imgv_todo.setOnClickListener {
            val serviceIntent = Intent(context, TodoService::class.java)
            serviceIntent.putExtra("todo_item_noti", todo_list[position])
            serviceIntent.action = "ACTION_PLAY"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context?.startForegroundService(serviceIntent)
            }
            context.startService(serviceIntent)
        }
    }

    override fun getItemCount(): Int {
        return todo_list.size
    }

    fun updateNotes(newNotes: List<Todo>) {
        todo_list = newNotes
        notifyDataSetChanged()
    }
}