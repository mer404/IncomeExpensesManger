package com.example.expensemanager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.expensemanager.R
import com.example.expensemanager.activity.AddDataActivity

class MenuAdapter( addDataActivity: AddDataActivity, mode1: Int, var mode: Array<String>) :
    BaseAdapter() {
    private var inflater: LayoutInflater = LayoutInflater.from(addDataActivity)
    override fun getCount(): Int {
        return mode.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view = inflater.inflate(R.layout.category_spinner, null, false)
//        var view: View = LayoutInflater.from(context).inflate(R.layout.category_spinner_item, null)
        val names = view.findViewById<TextView>(R.id.txtCategorySpinner)
        names.text = mode[position]
        return view
    }
}