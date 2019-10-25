package com.example.todoapp.add_edit

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.todoapp.R
import com.example.todoapp.data.database.TodoDB
import com.example.todoapp.data.entity.TodoItem
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_add_edit_todo.*
import java.util.*

class AddEditTodoActivity : AppCompatActivity() {

    private var id: Int? = null

    companion object {
        private const val KEY_ID = "id"

        fun createActivityIntent(context: Context, id: Int?): Intent {
            return Intent(context, AddEditTodoActivity::class.java).apply {
                if (id != null) {
                    putExtra(KEY_ID, id)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_todo)

        val actionBar = actionBar
        actionBar?.title = "Add Todo"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val database: TodoDB = TodoDB.getInstance(this)

        id = intent.getIntExtra("id", -1).also {
            if (it != -1) {
                val item = database.todoDao().getTodo(it)
                add_edit_til_title.editText?.setText(item.title)
                add_edit_til_start_date.editText?.setText(item.sDate)
                add_edit_til_end_date.editText?.setText(item.eDate)
                add_edit_til_memo.editText?.setText(item.memo)
            }
        }

        add_edit_ibtn_start_calender.setOnClickListener { v ->
            showCalender(add_edit_til_start_date)
        }

        add_edit_ibtn_end_calender.setOnClickListener { v ->
            showCalender(add_edit_til_end_date)
        }
    }

    private fun showCalender(til: TextInputLayout) {
        val cal = Calendar.getInstance()
        val mYear = cal.get(Calendar.YEAR)
        val mMonth = cal.get(Calendar.MONTH)
        val mDay = cal.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this@AddEditTodoActivity, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            til.editText!!.setText("" + year + "/" + (month + 1) + "/" + dayOfMonth)
        }, mYear, mMonth, mDay)
            .show()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.home -> finish()

            R.id.menu_save_item -> {
                val done1: Boolean = checkInput(add_edit_til_title)
                val done2: Boolean = checkInput(add_edit_til_start_date)
                val done3: Boolean = checkInput(add_edit_til_end_date)

                if (done1 && done2 && done3) saveData()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun checkInput(til: TextInputLayout): Boolean {
        return if (til.editText!!.text.toString() == "") {
            til.isErrorEnabled = true
            til.error = "input information"
            false
        } else {
            til.isErrorEnabled = false
            true
        }
    }

    private fun saveData() {
        val database: TodoDB = TodoDB.getInstance(this)
        if (id == -1) {
            // add item
            val newTodo = TodoItem(
                0, false,
                add_edit_til_title.editText!!.text.toString(),
                add_edit_til_start_date.editText!!.text.toString(),
                add_edit_til_end_date.editText!!.text.toString(),
                add_edit_til_memo.editText!!.text.toString()
            )
            database.todoDao().insertTodo(newTodo)
        } else {
            // edit item
            val item = database.todoDao().getTodo(id!!)
            item.title = add_edit_til_title.editText!!.text.toString()
            item.sDate = add_edit_til_start_date.editText!!.text.toString()
            item.eDate = add_edit_til_end_date.editText!!.text.toString()
            item.memo = add_edit_til_memo.editText!!.text.toString()
            database.todoDao().updateTodo(item)
        }

        finish()
    }
}
