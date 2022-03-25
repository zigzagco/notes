package com.example.notes.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.notes.adapter.ListItem
import java.util.*

class MyDbManager(context: Context) {
    val myDbHelper = MyDbHelper(context)
    var db:SQLiteDatabase? = null
    fun openDb(){
        db = myDbHelper.writableDatabase
    }
    fun incertToDb(title: String, content: String, settime: String, bool: String){
        val values = ContentValues().apply {
            put(MyDbNameClass.COLUMN_NAME_TITLE, title)
            put(MyDbNameClass.COLUMN_NAME_CONTENT, content)
            put(MyDbNameClass.COLUMN_NAME_SETTIME, settime)
            put(MyDbNameClass.COLUMN_NAME_BOOL, bool)

        }
        db?.insert(MyDbNameClass.TABLE_NAME, null, values)
    }

    fun updateBut(posit: String?, bool: String){
        val values = ContentValues().apply {
            put(MyDbNameClass.COLUMN_NAME_BOOL, bool)
        }
        db?.update(MyDbNameClass.TABLE_NAME, values, "_id=$posit", null)

    }

    fun updateToDb(posit: String?, title: String, content: String){
        val values = ContentValues().apply {
            put(MyDbNameClass.COLUMN_NAME_TITLE, title)
            put(MyDbNameClass.COLUMN_NAME_CONTENT, content)
        }
        db?.update(MyDbNameClass.TABLE_NAME, values, "_id=$posit", null)
    }
    fun deleteOneRow(posit: String?) {
        db?.delete(MyDbNameClass.TABLE_NAME, "_id=?", arrayOf(posit))
    }
    fun maxId():String {
        var idd = ""
        val qew = "SELECT _id FROM my_table ORDER BY _id DESC LIMIT 1"
        val cursor = myDbHelper.readableDatabase.rawQuery(qew, null)
        while (cursor?.moveToNext()!!){
            val dataId = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_ID))
            idd = dataId

        }
        cursor.close()
        return idd
    }
    fun favour():ArrayList<ListItem> {
        val dataList = ArrayList<ListItem>()
        val qew = "SELECT title,content,settime,_id,bool FROM my_table WHERE bool = '1'"
        val cursor = myDbHelper.readableDatabase.rawQuery(qew, null)

        while (cursor?.moveToNext()!!){

            val dataText = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_TITLE))
            val dataCont = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_CONTENT))
            val dataId = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_ID))
            val dataTime = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_SETTIME))
            val boolId = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_BOOL))
            val item = ListItem()
            item.title = dataText
            item.content = dataCont
            item.id = dataId
            item.time = dataTime
            item.bool = boolId
            dataList.add(item)
        }
        cursor.close()
        return dataList
    }
    var searchfield:String = ""
    fun resiveval(searchString: String){
        searchfield = searchString
    }
    fun firstSearch(): List<ListItem> {
        val dataList = ArrayList<ListItem>()
        val query = "SELECT title,content,settime,_id,bool FROM my_table WHERE title LIKE '$searchfield%'"
        val cursor = myDbHelper.readableDatabase.rawQuery(query, null)

        while (cursor?.moveToNext()!!){

            val dataText = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_TITLE))
            val dataCont = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_CONTENT))
            val dataId = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_ID))
            val dataTime = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_SETTIME))
            val boolId = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_BOOL))
            val item = ListItem()
            item.title = dataText
            item.content = dataCont
            item.id = dataId
            item.time = dataTime
            item.bool = boolId
            dataList.add(item)
        }
        cursor.close()
        return dataList
    }


    fun readDbData():ArrayList<ListItem>{
        val dataList = ArrayList<ListItem>()
        val qew = "SELECT title,content,settime,_id,bool FROM my_table WHERE bool = '0'"
        val cursor = myDbHelper.readableDatabase.rawQuery(qew, null)


            while (cursor?.moveToNext()!!){

                val dataText = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_TITLE))
                val dataCont = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_CONTENT))
                val dataId = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_ID))
                val dataTime = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_SETTIME))
                val boolId = cursor.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_BOOL))
                val item = ListItem()
                item.title = dataText
                item.content = dataCont
                item.id = dataId
                item.time = dataTime
                item.bool = boolId
                dataList.add(item)
            }
        cursor.close()

        return dataList

    }
    fun closeDb(){
        myDbHelper.close()
    }


}

