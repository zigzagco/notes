package com.example.notes.ListActivity

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.R
import com.example.notes.db.MyDbManager
import kotlinx.android.synthetic.main.activity_add_notes.*


class AddNotesActivity : AppCompatActivity() {
    val myDbManager = MyDbManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        setContentView(R.layout.activity_add_notes)
        overridePendingTransition(R.anim.bottom_in, R.anim.alpha)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        addTextId.setFocusableInTouchMode(true)
        addTextId.requestFocus()
        addTitleId.setRawInputType(InputType.TYPE_CLASS_TEXT);

    }

    override fun onRestart() {
        super.onRestart()
        if (currentFocus != null) currentFocus!!.clearFocus()
        val text = "Пора покормить кота!"
        val duration = Toast.LENGTH_SHORT

        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()

    }

    override fun onPause() {
        if (!isChangingConfigurations) {
            onClickUpdate()
        }
        super.onPause()
    }

     fun onClickBack(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }
    fun onClickUpdate() {
        myDbManager.openDb()
        val posit = myDbManager.maxId()
        if ((addTitleId.text.toString().equals("")) && (addTextId.text.toString().equals(""))){
        }
        else if ((addTitleId.text.toString().equals(""))){
            myDbManager.updateToDb(posit, addTitleId.text.toString(), addTextId.text.toString())
        }
        else {
            myDbManager.updateToDb(posit, addTitleId.text.toString(), addTextId.text.toString())
        }

    }
    

    /*override fun onPause() {
        super.onPause()
        val text = "Пора покормить кота!"
        val duration = Toast.LENGTH_SHORT

        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()
        onClickSave()
    }*/

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}