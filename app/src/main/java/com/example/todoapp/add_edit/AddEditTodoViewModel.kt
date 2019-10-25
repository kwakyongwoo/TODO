package com.example.todoapp.add_edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoapp.data.entity.TodoItem

class AddEditTodoViewModel : ViewModel() {
    val title = MutableLiveData<String>()
    val sDate = MutableLiveData<String>()
    val eDate = MutableLiveData<String>()
    val memo = MutableLiveData<String>()

    fun initialze(data: TodoItem) {
        title.value = data.title
        sDate.value = data.sDate
        eDate.value = data.eDate
        memo.value = data.memo
    }
}