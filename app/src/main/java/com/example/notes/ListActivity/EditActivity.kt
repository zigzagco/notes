package com.example.notes.ListActivity

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.Selection.setSelection
import android.text.Spannable
import android.text.TextWatcher
import android.text.style.StyleSpan
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.R
import com.example.notes.db.MyDbManager
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_edit.view.*
import kotlinx.android.synthetic.main.activity_main.*


class EditActivity : AppCompatActivity() {

    val myDbManager = MyDbManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        setContentView(R.layout.activity_edit)
        getAndSetIntentData()
        var TYPEFACE_NORMAL = 0
        var TYPEFACE_BOLD = 1
        var TYPEFACE_ITALICS = 2
        var TYPEFACE_BOLD_ITALICS = 3
        var lastCursorPosition:Int=0;
        var endCursorPosition:Int=0;
        var typefaceStyle:Int
        var currentTypeface=Typeface.NORMAL
        fun changeTypeface(tfId: Int) {
            currentTypeface = tfId;
            lastCursorPosition = editTextId.selectionStart;

        }

        checkBox.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked){
                typefaceStyle = Typeface.ITALIC
                changeTypeface(typefaceStyle)
            }else{
                typefaceStyle = Typeface.NORMAL
            }
            editTextId.getSelectionStart();
            editTextId.getSelectionEnd();
            changeTypeface(typefaceStyle)
        }
        checkBox2.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked){
                typefaceStyle = Typeface.BOLD
            }else{
                typefaceStyle = Typeface.NORMAL
            }
            editTextId.getSelectionStart();
            editTextId.getSelectionEnd();
            changeTypeface(typefaceStyle)
        }



        editTextId.addTextChangedListener(object : TextWatcher {


            override fun afterTextChanged(s: Editable) {


            }

            override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
            ) {
                val ss: StyleSpan
                ss = when (currentTypeface) {
                    TYPEFACE_NORMAL -> StyleSpan(Typeface.NORMAL)
                    TYPEFACE_BOLD -> StyleSpan(Typeface.BOLD)
                    TYPEFACE_ITALICS -> StyleSpan(Typeface.ITALIC)
                    TYPEFACE_BOLD_ITALICS -> StyleSpan(Typeface.BOLD_ITALIC)
                    else -> StyleSpan(Typeface.NORMAL)
                }
                val start: Int = editTextId.getSelectionStart()
                val end = s.length
                val flag = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                editTextId.text.setSpan(ss, lastCursorPosition, end, flag)
                editTitleId.setText(lastCursorPosition.toString() + "  " + end.toString())
                //editTitleId.setText(editTextId.selectionStart.toString() +"  "+ editTextId.selectionEnd.toString())


            }
        })

    }





    fun onClickBack(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        onClickSave()
        startActivity(intent)
    }
    fun onClickDell(view: View){
       myDbManager.openDb()
        val positit = (intent.getStringExtra("id"))
        myDbManager.deleteOneRow(positit.toString())
        finish()
    }
    fun onClickSave() {
        myDbManager.openDb()
        val positit = (intent.getStringExtra("id"))
        val titletext = editTitleId.text.toString()
        val edittext = editTextId.text.toString()
        if ((editTitleId.text.toString().equals("")) && (editTextId.text.toString().equals(""))){
            finish()
        }
        else {
            myDbManager.updateToDb(positit, titletext, edittext)

            finish()
        }

    }
    fun getAndSetIntentData() {
        if (intent.hasExtra("id")) {
            editTitleId.setText(intent.getStringExtra("title"))
            editTextId.setText(intent.getStringExtra("content"))
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onResume(){
        super.onResume()
        editTitleId.setSelection(editTitleId.getText().length, editTitleId.getText().length);


    }


}


