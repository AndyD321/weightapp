package com.example.myapplication2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.content.DialogInterface
import android.support.v7.app.AlertDialog


class userinformation_mainactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.userinformation_mainactivity_dialog)

        val next1 = findViewById<Next1>(R.id.next1)
            next1.setOnClickListener{
                val intent = Intent(this, weightInput::class.java)
                startActivity(intent)
            }
    }

    //method for saving records in database
    fun confirmRecord(view: View){
        val name = u_name.text.toString()
        val weight = u_weight.text.toString()
        val height = u_height.text.toString()
        val databaseHandler: DatabaseHandler= DatabaseHandler(this)
        if(name.trim()!="" && weight.trim()!="" && height.trim()!=""){
            val status = databaseHandler.addUser(UsrModelClass(Double.parseDouble(weight),Double.parseDouble(height), name))
            if(status > -1){
                Toast.makeText(applicationContext,"Saved successfully!",Toast.LENGTH_LONG).show()
                u_name.text.clear()
                u_weight.text.clear()
                u_height.text.clear()
            }
        }else{
            Toast.makeText(applicationContext,"Name or Weight or Height cannot be blank!",Toast.LENGTH_LONG).show()
        }
    }

}