package com.example.todoapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class TodoItem(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var checked: Boolean,
    var title: String,
    var sDate: String,
    var eDate: String,
    var memo: String
) {
    constructor(time: Long) : this(0, false, time.toString(), "", "", "")
}