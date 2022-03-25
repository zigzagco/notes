package com.example.notes.ListActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.R
import com.example.notes.adapter.MainAdapterK
import com.example.notes.db.MyDbManager
import kotlinx.android.synthetic.main.activity_favourites.*

class FavouritesSearch : AppCompatActivity() {
    val myDbManager = MyDbManager(this)
    val mainAdapter = MainAdapterK(ArrayList(),this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        setContentView(R.layout.activity_favourites)
        favourit_view.layoutManager  = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        favourit_view.adapter = mainAdapter
        window.setWindowAnimations(0)

        bottom_navigation.setOnNavigationItemSelectedListener{
            val nextActivity =
                    when(it.itemId){
                        R.id.search_menu -> ActivitySearch::class.java
                        R.id.add_menu -> AddNotesActivity::class.java
                        R.id.activitySettings -> ActivitySettings::class.java
                        else -> {null}
                    }
            if (nextActivity!=null){
                val intent = Intent(this, nextActivity)
                startActivity(intent)
                true
            }else{
                false
            }
        }
        bottom_navigation.menu.getItem(3).isChecked = true


    }
    override fun onResume(){
        super.onResume()
        myDbManager.openDb()
        mainAdapter.updateAdapter(myDbManager.favour())

    }
    /*fun onClickSave(view: View) {
        myDbManager.incertToDb(idTitle.text.toString(), idText.text.toString())
       // val dataList = myDbManager.readDbData()

    }*/


    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }

}


