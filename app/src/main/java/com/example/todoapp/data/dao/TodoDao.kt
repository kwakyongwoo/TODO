package com.example.todoapp.data.dao

import androidx.room.*
import com.example.todoapp.data.entity.TodoItem

@Dao
interface TodoDao {
    @Insert
    fun insertTodo(item: TodoItem)

    @Delete
    fun deleteTodo(item: TodoItem)

    @Update
    fun updateTodo(item: TodoItem)

    @Query("SELECT * FROM todo")
    fun getAllTodos() : List<TodoItem>

    @Query("SELECT * FROM todo WHERE id = :id")
    fun getTodo(id: Int) : TodoItem
}