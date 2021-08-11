package com.example.myapplication2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteException

//creating the database logic, extending the SQLiteOpenHelper base class
class DatabaseHandler(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "UserWeightDatabase"
        private val TABLE_CONTACTS = "UserWeightTable"
        private val KEY_WEIGHTINPUT = "weightinput"
        private val KEY_DATE = "date"
        private val KEY_NAME = "name"
        private val KEY_WEIGHT = "weight"
        private val KEY_HEIGHT = "height"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        //creating table with fields
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_WEIGHTINPUT + " DOUBLE PRIMARY NUMBERs,"
                + KEY_DATE + " TEXT" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS)
        onCreate(db)
    }


    //method to insert data
    fun addUser(usr: UsrModelClass):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, usr.userName)
        contentValues.put(KEY_WEIGHT, usr.userWeight)
        contentValues.put(KEY_HEIGHT,usr.userHeight)
        // Inserting Row
        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    fun addWeight(wei: WeiModelClass):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_WEIGHTINPUT, wei.weightInputData)
        contentValues.put(KEY_DATE, wei.dateData) // WeiModelClass Name
        // Inserting Row
        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    //method to read data
    fun viewWeight():List<WeiModelClass>{
        val weiList:ArrayList<WeiModelClass> = ArrayList<WeiModelClass>()
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var weightInputData: Double
        var dateData: String
        if (cursor.moveToFirst()) {
            do {
                weightInputData = cursor.getDouble(cursor.getColumnIndex("weightinput"))
                dateData = cursor.getString(cursor.getColumnIndex("date"))
                val wei= WeiModelClass(weightInputData = weightInputData, dateData = dateData)
                weiList.add(wei)
            } while (cursor.moveToNext())
        }
        return weiList
    }

    //method to delete data
    fun deleteWeight(wei: WeiModelClass):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_DATE, wei.dateData) // WeiModelClass dateData
        // Deleting Row
        val success = db.delete(TABLE_CONTACTS,"id="+wei.dateData,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
}