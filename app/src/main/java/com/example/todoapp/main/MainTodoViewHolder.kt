package com.example.todoapp.main

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.entity.TodoItem

class MainTodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun onBind(item: TodoItem) {
        itemView.findViewById<TextView>(R.id.todo_tv_title).text = item.title
        itemView.findViewById<CheckBox>(R.id.todo_cb).isChecked = item.checked
    }
}