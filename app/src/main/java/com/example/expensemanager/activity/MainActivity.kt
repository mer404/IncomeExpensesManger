package com.example.expensemanager.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.expensemanager.R

class MainActivity : AppCompatActivity() {
    var link: String =
        "https://play.google.com/store/apps/details?id=com.mlab.expense.manager&hl=en_IN&gl=US"
    lateinit var rlvIncome: RelativeLayout
    lateinit var rlvTransaction: RelativeLayout
    lateinit var rlvExpenses: RelativeLayout
    lateinit var rlvReport: RelativeLayout
    lateinit var rlvCategory: RelativeLayout
    lateinit var premium: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        premium()
    }

    private fun premium() {
        premium = findViewById(R.id.premium)
        premium.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data =
                Uri.parse("https://play.google.com/store/apps/details?id=com.mlab.expense.manager&hl=en_IN&glUS")
            startActivity(i)
        }
    }

    private fun initView() {
        rlvIncome = findViewById(R.id.rlvIncome)
        rlvExpenses = findViewById(R.id.rlvExpenses)
        rlvTransaction = findViewById(R.id.rlvTransaction)
        rlvReport = findViewById(R.id.rlvReport)
        rlvCategory = findViewById(R.id.rlvCategory)

        rlvIncome.setOnClickListener {
            val intent = Intent(this, AddDataActivity::class.java)
            intent.putExtra("income", "ADD INCOME")
            intent.putExtra("type", 0)
            startActivity(intent)
        }
        rlvExpenses.setOnClickListener {
            var intent = Intent(this, AddDataActivity::class.java)
            intent.putExtra("expenses", "ADD EXPENSES")
            intent.putExtra("type", 1)
            startActivity(intent)
        }
        rlvTransaction.setOnClickListener {
            var intent = Intent(this, AllTransactionActivity::class.java)
            startActivity(intent)
        }
        rlvReport.setOnClickListener {
            var intent = Intent(this, ReportActvity::class.java)
            startActivity(intent)
        }
        rlvCategory.setOnClickListener {
            var i = Intent(this, AddCategoryActivity::class.java)
            startActivity(i)
        }
    }

}