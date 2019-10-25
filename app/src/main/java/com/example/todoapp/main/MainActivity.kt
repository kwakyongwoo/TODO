package com.example.todoapp.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.add_edit.AddEditTodoActivity
import com.example.todoapp.data.database.TodoDB
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var database: TodoDB? = null
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MainAdapter(this)
        adapter.initialize()
        database = TodoDB.getInstance(this)

        main_rcv_todo.adapter = adapter
        main_rcv_todo.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        main_rcv_todo.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        main_fab_add.setOnClickListener { v ->
//            val time = System.currentTimeMillis()
//            adapter.addItem(TodoItem(time))

            startActivity(AddEditTodoActivity.createActivityIntent(this, null))
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.initialize()
    }
}
