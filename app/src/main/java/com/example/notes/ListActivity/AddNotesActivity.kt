package com.example.notes.ListActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.R
import com.example.notes.adapter.ListItem
import com.example.notes.db.MyDbManager
import com.example.notes.db.MyDbNameClass
import kotlinx.android.synthetic.main.activity_add_notes.*

class AddNotesActivity : AppCompatActivity() {
    val myDbManager = MyDbManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)
    }
    /*override fun getIntent(): Intent {
        val i: Intent = intent
        val item = i.getSerializableExtra(MyDbNameClass.ITEM_INTENT) as Listitem?
        idTitle.setText(item.title)
        idText.setText(item.content)
    }*/

    fun onClickSave(view: View) {
        myDbManager.openDb()
        //myDbManager.incertToDb(idTitle.text.toString(),idText.text.toString())
        //val dataList = myDbManager.readDbData()
        if ((idTitle.text.toString().equals("")) && (idText.text.toString().equals(""))){
            finish()
        }
        else {
            myDbManager.incertToDb(idTitle.text.toString(), idText.text.toString());
            finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }
}