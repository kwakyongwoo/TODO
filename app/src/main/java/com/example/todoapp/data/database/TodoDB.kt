package com.example.todoapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapp.data.dao.TodoDao
import com.example.todoapp.data.entity.TodoItem

@Database(entities = [(TodoItem::class)], version = 1)
abstract class TodoDB : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        private var instance: TodoDB? = null

        fun getInstance(context: Context): TodoDB {
            synchronized(TodoDB::class) {
                if (instance == null)
                    instance = Room.databaseBuilder(context.applicationContext, TodoDB::class.java, "todo.db")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
            }

            return instance!!
        }
    }
}