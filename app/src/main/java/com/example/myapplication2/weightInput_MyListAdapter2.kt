package com.example.myapplication2

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MyListAdapter2(private val context: Activity, private val date: Array<String>, private val weightInput: Array<Double>)
    : ArrayAdapter<String>(context, R.layout.custom_list, weightInput) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list, null, true)

        val weightInputText = rowView.findViewById(R.id.textViewWeightInput) as TextView
        val dateText = rowView.findViewById(R.id.textViewDate) as TextView

        weightInputText.text = "Weight: ${weightInput[position]}"
        dateText.text = "Date: ${date[position]}"
        return rowView
    }
}
