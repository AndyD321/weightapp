package com.example.myapplication2

import android.view.View
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog

class weightInput : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weightinput_activity_dialog)
    }

//method for saving weight and date input in database
fun saveWeightDate(view: View){
    val weightInput = u_weightInput.text.toString()
    val date = u_date.text.toString()
    val databaseHandler: DatabaseHandler= DatabaseHandler(this)
    if(weightInput.trim()!="" && date.trim()!=""){
        val status = databaseHandler.addWeight(WeiModelClass(Double.parseDouble(weightInput),date))
        if(status > -1){
            Toast.makeText(applicationContext,"Saved successfully!",Toast.LENGTH_LONG).show()
            u_weightInput.text.clear()
            u_date.text.clear()
        }
    }else{
        Toast.makeText(applicationContext,"Weight and date cannot be blank!",Toast.LENGTH_LONG).show()
    }
}

//method for read weight and date input from database in ListView
fun viewWeightDate(view: View){
    //creating the instance of DatabaseHandler class
    val databaseHandler: DatabaseHandler= DatabaseHandler(this)
    //calling the viewWeight method of DatabaseHandler class to read the records
    val wei: List<WeiModelClass> = databaseHandler.viewWeight()
    val weiArrayWeightInput = Array<String>(wei.size){"0"}
    val weiArrayDate = Array<String>(wei.size){"null"}
    var index = 0
    for(e in wei){
        weiArrayWeightInput[index] = e.weightInputData.toString()
        weiArrayDate[index] = e.dateData
        index++
    }

    //creating custom ArrayAdapter2
    val myListAdapter2 = MyListAdapter2(this,weiArrayWeightInput,weiArrayDate)
    listView.adapter2 = myListAdapter2
}

//method for delete data based on date
fun deleteWeightDate(view: View){
    //creating AlertDialog for taking date
    val dialogBuilder = AlertDialog.Builder(this)
    val inflater = this.layoutInflater
    val dialogView = inflater.inflate(R.layout.weightInputDelete_dialog, null)
    dialogBuilder.setView(dialogView)

    val dltWeightDate = dialogView.findViewById(R.id.deleteWeightDate) as EditText
    dialogBuilder.setTitle("Delete Data")
    dialogBuilder.setMessage("Enter the date you want to delete below:")
    dialogBuilder.setPositiveButton("Delete", DialogInterface.OnClickListener { _, _ ->

        val deleteWeightDate = dltWeightDate.text.toString()
        //creating the instance of DatabaseHandler class
        val databaseHandler: DatabaseHandler= DatabaseHandler(this)
        if(deleteWeightDate.trim()!=""){
            //calling the deleteWeight method of DatabaseHandler class to delete record
            val status = databaseHandler.deleteWeight(WeiModelClass(deleteWeightDate))
            if(status > -1){
                Toast.makeText(applicationContext,"Deleted Successfully!",Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(applicationContext,"The fill cannot be blank!",Toast.LENGTH_LONG).show()
        }

    })
    dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { _, _ ->
        //pass
    })
    val b = dialogBuilder.create()
    b.show()
}
}

