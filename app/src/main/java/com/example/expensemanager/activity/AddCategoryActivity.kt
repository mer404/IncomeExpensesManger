package com.example.expensemanager.activity

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.expensemanager.R
import com.example.expensemanager.classes.DataBaseHelper

class AddCategoryActivity : AppCompatActivity() {
    lateinit var apbtnAddCategory: AppCompatButton
    lateinit var edtAddCategory: EditText
    var type = 0
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
                Toast.makeText(this, "Add Category Successfully", Toast.LENGTH_SHORT).show()
                var intent = Intent(this, AddDataActivity::class.java)
                intent.putExtra("type", type)
                startActivity(intent)
                finish()
            }
        }
    }
}