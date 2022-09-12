package com.example.expensemanager.activity

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.expensemanager.classes.DataBaseHelper
import com.example.expensemanager.R

class AddCategoryActivity : AppCompatActivity() {
    lateinit var apbtnAddCategory: AppCompatButton
    lateinit var edtAddCategory: EditText
    val dbHelper = DataBaseHelper(this, "ExpensesManager.db", null, 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)
        initView()
    }
    private fun initView() {
        apbtnAddCategory = findViewById(R.id.apbtnAddCategory)
        edtAddCategory = findViewById(R.id.edtAddCategory)
        apbtnAddCategory.setOnClickListener {
            val category = edtAddCategory.text.toString()
            if (category.isEmpty()) {
                Toast.makeText(this, "Please Enter Category", Toast.LENGTH_SHORT).show()
            } else {
                dbHelper.insertCategory(category)
                edtAddCategory.setText("")
                Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        }
    }
}