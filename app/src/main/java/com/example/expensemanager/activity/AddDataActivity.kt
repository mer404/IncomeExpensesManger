package com.example.expensemanager.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.expensemanager.R
import com.example.expensemanager.adapter.CategoryAdapter
import com.example.expensemanager.adapter.MenuAdapter
import com.example.expensemanager.classes.DataBaseHelper
import com.example.expensemanager.classes.modelClasses.CategoryModelClass
import java.util.*


class AddDataActivity : AppCompatActivity() {
    lateinit var incomeBack: ImageView
    lateinit var btnSaveExpenses: ImageView
    lateinit var spinnerMode: Spinner
    lateinit var edtAmountIncome: EditText
    lateinit var edtNoteincome: EditText
    lateinit var txtDateIncome: TextView
    lateinit var txtManager: TextView
    lateinit var rdIncome: RadioButton
    lateinit var rdExpensesIncome: RadioButton
    lateinit var rdGroup: RadioGroup
    lateinit var txtTime: TextView
    lateinit var spinnerCategoryIncome: Spinner
    var TYPE: Int = 0
    var modeSpinner: String = ""
    var selectedMode: String = ""
    var categorySelected: String = " "
    var categoryList = ArrayList<CategoryModelClass>()
    var mode = arrayOf("Cash", "Paytm", "GPay", "Paypal")
    val dbHelper = DataBaseHelper(this, "ExpensesManager.db", null, 1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data)
        initView()
        date()
        spinner()
        category()
    }

    private fun initView() {
        incomeBack = findViewById(R.id.incomeBack)
        edtAmountIncome = findViewById(R.id.edtAmountIncome)
        edtNoteincome = findViewById(R.id.edtNoteincome)
        btnSaveExpenses = findViewById(R.id.btnSaveExpenses)
        txtManager = findViewById(R.id.txtManager)
        rdExpensesIncome = findViewById(R.id.rdExpensesIncome)
        rdIncome = findViewById(R.id.rdIncome)
        rdGroup = findViewById(R.id.rdGroup)
        txtTime = findViewById(R.id.txtTime)
        spinnerMode = findViewById(R.id.spMode)
        spinnerCategoryIncome = findViewById(R.id.spinnerCategoryIncome)
        var id = rdGroup.checkedRadioButtonId
        if (intent.hasExtra("income")) {
            txtManager.setText("ADD INCOME")
            rdIncome.isChecked = true
        } else if (intent.hasExtra("expenses")) {
            txtManager.setText("ADD EXPENSES")
//            rdExpensesIncome.text.toString()
            intent.getIntExtra("type", 0)
            rdExpensesIncome.isChecked = true
            if (intent.hasExtra("income")) {
                TYPE = 0
            } else {
                TYPE = 1
            }
        }
        incomeBack.setOnClickListener {
            onBackPressed()
        }
        btnSaveExpenses.setOnClickListener {
            save()
        }
        txtTime.setOnClickListener {
            time()
        }
    }

    private fun date() {
        txtDateIncome = findViewById(R.id.txtDateIncome)
        val cal: Calendar = Calendar.getInstance()
        txtDateIncome.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this, { view, year, monthOfYear, dayOfMonth ->
                    val selectedDate: String =
                        dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year
                    txtDateIncome.setText(selectedDate)
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.datePicker.maxDate = cal.timeInMillis
            datePickerDialog.show()
        }
    }

    private fun category() {

        categoryList = dbHelper.display()
        val categoryAdapter = CategoryAdapter(this, R.layout.category_spinner, categoryList)
        spinnerCategoryIncome.adapter = categoryAdapter


        val mAdapter = MenuAdapter(this, R.layout.category_spinner, mode)
        spinnerMode.adapter = mAdapter
        if (spinnerCategoryIncome != null) {
            val adapter = CategoryAdapter(this, R.layout.category_spinner, categoryList)
            spinnerCategoryIncome.adapter = adapter
            spinnerCategoryIncome.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
                        position: Int,
                        id: Long
                    ) {
                        categorySelected = categoryList[position].name
//                        Log.e("TAG", "onItemSelected: ===>" + categorySelected)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // write code to perform some action
                    }
                }
        }
        if (spinnerMode != null) {
            val adapter = MenuAdapter(this, R.layout.category_spinner, mode)
            spinnerMode.adapter = adapter

            spinnerMode.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
                        position: Int,
                        id: Long
                    ) {
                        selectedMode = mode[position]
//                        Log.e("TAG", "onItemSelected: ===>" + selectedMode)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // write code to perform some action
                    }
                }
        }
    }

    private fun time() {
        val date_time = ""
        var mHour: Int
        var mMinute: Int
        val c = Calendar.getInstance()
        mHour = c[Calendar.HOUR_OF_DAY]
        mMinute = c[Calendar.MINUTE]
        val timePickerDialog = TimePickerDialog(
            this,
            { view, hourOfDay, minute ->
                mHour = hourOfDay
                mMinute = minute
                txtTime.text = "$date_time $hourOfDay:$minute"
            }, mHour, mMinute, true
        )
        timePickerDialog.show()
    }

    private fun spinner() {
        categoryList = dbHelper.display()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryList)
        spinnerMode.adapter = adapter
        spinnerMode.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    selectedMode = categoryList[position].name
                    Log.e("TAG", "onItemSelected: ====> $selectedMode")
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
    }

    private fun save() {
        val amount = edtAmountIncome.text.toString()
        val notes = edtNoteincome.text.toString()
        val date = txtDateIncome.text.toString()
        val time = txtTime.text.toString()
        val type = TYPE
        if (amount.isEmpty()) {
            Toast.makeText(this, "Please Enter Amount", Toast.LENGTH_SHORT).show()
        } else if (notes.isEmpty()) {
            Toast.makeText(this, "Please Enter Some Note", Toast.LENGTH_SHORT).show()
        } else if (date.isEmpty()) {
            Toast.makeText(this, "Please select date", Toast.LENGTH_SHORT).show()
        } else if (time.isEmpty()) {
            Toast.makeText(this, "Please Select Date", Toast.LENGTH_SHORT).show()
        } else {
            dbHelper.insertData(amount, categorySelected, date, selectedMode, notes, time, type)
            Toast.makeText(this, "Added Record Successfully", Toast.LENGTH_SHORT).show()
            var i = Intent(this, AllTransactionActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}



