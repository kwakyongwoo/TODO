package com.example.todoapp.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.add_edit.AddEditTodoActivity
import com.example.todoapp.data.database.TodoDB
import com.example.todoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var database: TodoDB? = null
    private var adapter: MainAdapter? = null
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        adapter = MainAdapter(this)
        adapter?.initialize()
        database = TodoDB.getInstance(this)

        binding?.mainRcvTodo?.adapter = adapter
        binding?.mainRcvTodo?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding?.mainRcvTodo?.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        binding?.mainFabAdd?.setOnClickListener { v ->
//            val time = System.currentTimeMillis()
//            adapter.addItem(TodoItem(time))

            startActivity(AddEditTodoActivity.createActivityIntent(this, null))
        }
    }

    override fun onResume() {
        super.onResume()
        adapter?.initialize()
    }
}
