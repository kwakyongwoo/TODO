package com.example.todoapp.add_edit

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.todoapp.R
import com.example.todoapp.data.database.TodoDB
import com.example.todoapp.data.entity.TodoItem
import com.example.todoapp.databinding.ActivityAddEditTodoBinding
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class AddEditTodoActivity : AppCompatActivity() {
    private var id: Int? = null
    private var binding: ActivityAddEditTodoBinding? = null
    private var viewModel: AddEditTodoViewModel? = null

    companion object {
        private const val KEY_ID = "id"

        private const val CLICK_START_DATE = 0
        private const val CLICK_END_DATE = 1

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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_edit_todo)
        binding?.lifecycleOwner = this

        viewModel = ViewModelProviders.of(this)
            .get(AddEditTodoViewModel::class.java)
            .also { binding?.viewModel = it }

        val actionBar = actionBar
        actionBar?.title = "Add Todo"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val database: TodoDB = TodoDB.getInstance(this)

        id = intent.getIntExtra("id", -1).also {
            if (it != -1) {
                val item = database.todoDao().getTodo(it)
                viewModel?.initialize(item)
            }
        }

        binding?.addEditIbtnStartCalender?.setOnClickListener { v ->
            showCalender(CLICK_START_DATE)
        }

        binding?.addEditIbtnEndCalender?.setOnClickListener { v ->
            showCalender(CLICK_END_DATE)
        }

        binding?.addEditTilStartDate?.setOnClickListener { v ->
            showCalender(CLICK_START_DATE)
        }

        binding?.addEditTilEndDate?.setOnClickListener { v ->
            showCalender(CLICK_END_DATE)
        }
    }

    private fun showCalender(value: Int) {
        val cal = Calendar.getInstance()
        val mYear = cal.get(Calendar.YEAR)
        val mMonth = cal.get(Calendar.MONTH)
        val mDay = cal.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this@AddEditTodoActivity, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            if (value == CLICK_START_DATE) viewModel?.sDate?.value = "" + year + "/" + (month + 1) + "/" + dayOfMonth
            else if (value == CLICK_END_DATE) viewModel?.eDate?.value = "" + year + "/" + (month + 1) + "/" + dayOfMonth
        }, mYear, mMonth, mDay)
            .show()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.home -> finish()

            R.id.menu_save_item -> {
                if (checkInput(binding?.addEditTilTitle) &&
                    checkInput(binding?.addEditTilStartDate) &&
                    checkInput(binding?.addEditTilEndDate)
                ) {
                    saveData()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun checkInput(view: TextInputLayout?): Boolean {
        if (view == null) return false
        return if (view.editText!!.text.toString() == "") {
            view.isErrorEnabled = true
            view.error = "input information"
            false
        } else {
            view.isErrorEnabled = false
            true
        }
    }

    private fun saveData() {
        val database: TodoDB = TodoDB.getInstance(this)
        viewModel?.let { viewModel ->
            if (id == -1) {
                // add item
                val newTodo = TodoItem(
                    0, false,
                    viewModel.title.value!!, viewModel.sDate.value!!, viewModel.eDate.value!!, viewModel.memo.value!!
                )
                database.todoDao().insertTodo(newTodo)
            } else {
                // edit item
                val item = database.todoDao().getTodo(id!!)
                item.title = viewModel.title.value!!
                item.sDate = viewModel.sDate.value!!
                item.eDate = viewModel.eDate.value!!
                item.memo = viewModel.memo.value!!

                database.todoDao().updateTodo(item)
            }

            finish()
        } ?: run {
            // Todo :: Error message
            finish()
        }
    }
}
