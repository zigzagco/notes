package com.example.notes.ListActivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.notes.R
import kotlinx.android.synthetic.main.activity_main.bottom_navigation

class FavouritesSearch : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)
        window.setWindowAnimations(0)

        bottom_navigation.setOnNavigationItemSelectedListener{
            val nextActivity =
                    when(it.itemId){
                        R.id.search_menu -> ActivitySearch::class.java
                        R.id.add_menu -> AddNotesActivity::class.java
                        R.id.favourites_menu -> MainActivity::class.java
                        //  R.id.settings_menu -> ActivitySettings::class.java
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

}