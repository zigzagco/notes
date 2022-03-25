package com.example.notes.ListActivity


//import com.example.notes.adapter.MainAdapterK

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.TranslateAnimation
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.adapter.MainAdapterK
import com.example.notes.adapter.RecAdapter
import com.example.notes.db.MyDbManager
import kotlinx.android.synthetic.main.activity_add_notes.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    val myDbManager = MyDbManager(this)
    val mainAdapter = MainAdapterK(ArrayList(), this)
    val Adapter = RecAdapter(ArrayList(), this)
    private var lastTranslate = 0.0f
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        //toggle = ActionBarDrawerToggle(this,drawer_layout,toolbar,R.string.open,R.string.close)
        toggle = object : ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.open,
            R.string.close
        ) {
            @SuppressLint("NewApi")
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                val moveFactor: Float = nav_view.getWidth() * slideOffset
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    app_bar_main.setTranslationX(moveFactor)
                } else {
                    val anim = TranslateAnimation(lastTranslate, moveFactor, 0.0f, 0.0f)
                    anim.duration = 0
                    anim.fillAfter = true
                    app_bar_main.startAnimation(anim)
                    lastTranslate = moveFactor
                }
            }
        }
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        setSupportActionBar(toolbar)
        nav_view.setBackgroundResource(R.drawable.r_corners)

        var flag = "1"
        recycler_list.visibility=View.GONE
        /*searchView.setOnSearchClickListener {
            searchView.animate().apply {
                duration = 350
                translationY(-40f)
            }
        }*/

        imageButton.setOnClickListener{
            val recheit = recycler_list.height.toFloat()
            if (flag == "0") {
                imageButton.animate().apply {
                    duration = 350
                    rotation(-90f)
                }.start()
                recycler_list.animate().apply {
                    duration = 100
                    //translationY(-recycler_list.height.toFloat())
                    //translationZBy(-1f)
                    //alpha(0F)
                    android.os.Handler().postDelayed({ recycler_list.visibility=View.GONE }, 0)
                }


                imageButton.isSelected = true
                flag = "1"



            } else { // возвращаем первую картинку
                imageButton.animate().apply {
                    duration = 350
                    rotation(0F)

                }.start()
                recycler_list.animate().apply {
                    duration = 180
                    // translationY(0f)
                    // translationZBy(-1f)
                    //alpha(1F)
                    android.os.Handler().postDelayed({ recycler_list.visibility=View.VISIBLE }, 0)

                }

                flag = "0"
            }


        }
        /*searchView.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                myDbManager.resiveval(p0.toString())
                mainAdapter.updateAdapter(myDbManager.firstSearch())
                val a = p0.toString()
                if (a.equals("")) {
                    mainAdapter.updateAdapter(myDbManager.firstSearch())
                }
                return true
            }

        })*/
        recycler_list.layoutManager  = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        recycler_list.adapter = Adapter
        idView.layoutManager  = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        idView.adapter = mainAdapter
        val swaphelp = getSwap()
        swaphelp.attachToRecyclerView(idView)
        window.setWindowAnimations(0)
        bottom_navigation.setOnNavigationItemSelectedListener OnNavigationItemSelectedListener@{
            when(it.itemId){
                R.id.search_menu ->{
                    val intent = Intent(this,ActivitySearch::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.add_menu -> {
                    val bool = "0"
                    val c = Calendar.getInstance()
                    val dateformat = SimpleDateFormat("dd MMMM yyyy г., HH:mm")
                    val datetime = dateformat.format(c.time)
                    myDbManager.incertToDb("Заметка без названия","",datetime, bool)

                    val intent = Intent(this,AddNotesActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.activitySettings ->{
                    val intent = Intent(this,ActivitySettings::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    startActivity(intent)
                    return@OnNavigationItemSelectedListener true

                }
                else -> {false}
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_multipie,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onRestart() {
        super.onRestart()
        mainAdapter.updateAdapter(myDbManager.readDbData())
        Adapter.updateAdapter(myDbManager.favour())
        recycler_list.invalidate()
        idView.invalidate()

    }


    override fun onBackPressed() {
        moveTaskToBack(true)
    }


    override fun onResume(){
        super.onResume()
        myDbManager.openDb()
        mainAdapter.updateAdapter(myDbManager.readDbData())
        Adapter.updateAdapter(myDbManager.favour())


    }
    override fun onDestroy() {
        super.onDestroy()
        myDbManager.closeDb()
    }

    private fun getSwap():ItemTouchHelper{
        return ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                mainAdapter.removeSwipe(viewHolder.adapterPosition, myDbManager)

            }

        })
    }
    fun foo1(){
        myDbManager.openDb()
        mainAdapter.updateAdapter(myDbManager.readDbData())
    }
    fun foo() {
        myDbManager.openDb()
        mainAdapter.updateAdapter(myDbManager.readDbData())
        Adapter.updateAdapter(myDbManager.favour())
    }
    fun no_elinrec(){
        Cardview_fav.visibility = View.GONE
        recycler_list.visibility = View.GONE
    }
    fun elinrec(){
        Cardview_fav.visibility = View.VISIBLE
        recycler_list.visibility = View.VISIBLE
    }
    fun elinrecc(){
        Cardview_fav.visibility = View.VISIBLE
    }

}


