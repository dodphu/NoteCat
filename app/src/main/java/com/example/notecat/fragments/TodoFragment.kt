package com.example.notecat.fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notecat.R
import com.example.notecat.adapter.AdapterTodo
import com.example.notecat.databinding.FragmentTodoBinding
import com.example.notecat.model.Todo
import com.example.notecat.service.TodoService
import com.example.notecat.viewmodel.TodoViewModel


class TodoFragment : Fragment(R.layout.fragment_todo) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentTodoBinding.bind(view)
        val rycv_todo = binding.rycvTodo

        val adaptertodo = AdapterTodo(requireContext())
        rycv_todo.adapter = adaptertodo
        rycv_todo.layoutManager = LinearLayoutManager(context)

        val fabtodo = binding.fabTodo
        val todoViewModel = ViewModelProvider(this)[TodoViewModel::class.java]

        todoViewModel.getAllTodoVM().observe(viewLifecycleOwner) { listtodo ->
            adaptertodo.updateNotes(listtodo)
        }

        fabtodo.setOnClickListener {
            val editText = EditText(context)
            val dialog = context?.let { it1 ->
                AlertDialog.Builder(it1)
                    .setTitle("Thêm công việc mới")
                    .setMessage("Nhập công việc:")
                    .setView(editText)
                    .setPositiveButton("Thêm") { dialog, _ ->
                        val todoText = editText.text.toString().trim()
                        if (todoText.isNotEmpty()) {
                            todoViewModel.addTodoVM(Todo(null, todoText, false))
                        }
                        dialog.dismiss()
                    }
                    .setNegativeButton("Hủy") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
            }

            if (dialog != null) {
                dialog.show()
            }
        }
    }
}