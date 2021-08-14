package com.example.myapplication2

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MyListAdapter(private val context: Activity, private val name: Array<String>, private val weight: Array<String>, private val height: Array<String>)
    : ArrayAdapter<String>(context, R.layout.userinformation_mainactivity_dialog, name) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.userinformation_mainactivity_dialog, null, true)

        val nameText = rowView.findViewById(R.id.u_name) as TextView

        nameText.text = "Name: ${name[position]}"
        return rowView
    }
}