package com.example.notes.ListActivity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.R
import com.example.notes.adapter.ListItem
import com.example.notes.adapter.MainAdapterK
import com.example.notes.db.MyDbManager
import kotlinx.android.synthetic.main.activity_search.*


class ActivitySearch : AppCompatActivity() {
    val myDbManager = MyDbManager(this)
    val mainAdapter = MainAdapterK(ArrayList(), this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        setContentView(R.layout.activity_search)
        overridePendingTransition(R.anim.right_in, R.anim.right_inn)
        sample_recyclerView.layoutManager  = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        sample_recyclerView.adapter = mainAdapter
        sample_editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                myDbManager.resiveval(p0.toString())
                mainAdapter.updateAdapter(myDbManager.firstSearch())
                val a = p0.toString()
                    if (a.equals("")) {

                        mainAdapter.ifSearchClear(myDbManager.firstSearch())
                    }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
    }

    fun onClickBack(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)


    }
    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }
}