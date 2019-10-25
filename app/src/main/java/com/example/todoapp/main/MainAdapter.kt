package com.example.todoapp.main

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.add_edit.AddEditTodoActivity
import com.example.todoapp.data.database.TodoDB
import com.example.todoapp.data.entity.TodoItem

class MainAdapter(context: Context): RecyclerView.Adapter<MainTodoViewHolder>() {

    private var itemList: MutableList<TodoItem> = mutableListOf()
    private var database: TodoDB = TodoDB.getInstance(context)

    fun initialize() {
        itemList.clear()
        itemList.addAll(database.todoDao().getAllTodos())
        notifyDataSetChanged()
    }

    // test
    fun addItem(item: TodoItem) {
        itemList.add(item)
        notifyItemInserted(itemList.size - 1)
        database.todoDao().insertTodo(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainTodoViewHolder {
        val viewHolder = MainTodoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false))

        viewHolder.itemView.setOnClickListener { v ->
            val position = viewHolder.adapterPosition
            itemList[position].checked = !itemList[position].checked
            database.todoDao().updateTodo(itemList[position])
            notifyItemChanged(position)
        }

        viewHolder.itemView.setOnLongClickListener { v ->
            val itemPosition = viewHolder.adapterPosition
            val list = arrayOf("수정", "삭제", "취소")

            AlertDialog.Builder(v.context)
                .setTitle(itemList[itemPosition].title)
                .setItems(list) { dialog, position ->
                    when (list[position]) {
                        "수정" -> {
                            v.context.startActivity(AddEditTodoActivity.createActivityIntent(v.context, itemList[itemPosition].id))
                        }

                        "삭제" -> {
                            database.todoDao().deleteTodo(itemList[itemPosition])
                            itemList.removeAt(itemPosition)
                            notifyItemRemoved(itemPosition)
                        }

                        "취소" -> {
                        }
                    }
                }
                .show()
            true
        }

        return viewHolder
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MainTodoViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }

}