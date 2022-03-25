package com.example.notes.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.ListActivity.EditActivity
import com.example.notes.ListActivity.MainActivity
import com.example.notes.R
import com.example.notes.db.MyDbManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheed.*
import kotlinx.android.synthetic.main.item_view.view.*
import java.util.*


class MainAdapterK(listMain: ArrayList<ListItem>, contextMain: Context) :RecyclerView.Adapter<MainAdapterK.ViewHolder>() {
    val mainArray = listMain
    val context = contextMain
    val myDbManager = MyDbManager(context)

    var selectedlist = mutableListOf<Int>()
    private fun getBaseContext(): Context? {
    return context
    }


    class ViewHolder(itemView: View, contextView: Context) : RecyclerView.ViewHolder(itemView) {
        private val idTitle: TextView
        private val idContent: TextView
        //val button:ImageButton
        val textView : TextView

        fun setTitle(item: ListItem): String {
            val r1 = item.title
            return (r1)
        }
        fun setContent(item: ListItem): String {
            val r2 = item.content
            return (r2)
        }
        fun setIDD(item: ListItem): String {
            val r3 = item.id
            return (r3)
        }
        fun setData(item: ListItem) {
            idTitle.text = item.title
            idContent.text = item.content
            textView.text = item.time
        }

        init {
            idTitle = itemView.findViewById(R.id.Id_Title)
            idContent = itemView.findViewById(R.id.Id_Content)
            //button = itemView.findViewById(R.id.Button2)
            textView = itemView.findViewById(R.id.textView)
            //textView3 =itemView.findViewById(R.id.textView3)

        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_view, parent, false), context)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var isSelected = false
        //holder.itemView.idCard.setBackgroundColor(if (isSelected()) Color.CYAN else Color.WHITE)
        fun setSelected(selected: Boolean) {
            isSelected = selected
        }

        fun isSelected(): Boolean {
            return isSelected
        }
        holder.setData(mainArray[position])
        holder.itemView.imageView.setImageResource(R.mipmap.ic_launcher)
       holder.itemView.setOnClickListener { v ->
            val context = v.context
           val r3 = holder.setIDD(mainArray[position])
            val r1 = holder.setTitle(mainArray[position])
           val r2 = holder.setContent(mainArray[position])
           val intent = Intent(context, EditActivity::class.java)
           intent.putExtra("id", r3)
           intent.putExtra("title", r1)
           intent.putExtra("content", r2)

            context.startActivity(intent)

    }
        holder.itemView.setOnLongClickListener {
            Toast.makeText(getBaseContext(), "Clicked", Toast.LENGTH_SHORT).show();
            setSelected(!isSelected);
            holder.itemView.idCard.setCardBackgroundColor(if (isSelected()) Color.CYAN else Color.WHITE)

            holder.itemView.idCard.textView3.text = itemCount.toString()

            val view = LayoutInflater.from(context).inflate(R.layout.bottom_sheed, null)
            val dialog = BottomSheetDialog(context)
            dialog.setContentView(view)
            dialog.show()
            dialog.linearLayout2.setOnClickListener {
                myDbManager.openDb()
                Toast.makeText(getBaseContext(), "Del", Toast.LENGTH_SHORT).show();
                removebycheck(position, myDbManager)
                (context as MainActivity).foo()
                dialog.dismiss()
            }
            dialog.linearLayout3.setOnClickListener {
                Toast.makeText(getBaseContext(), "Fav", Toast.LENGTH_SHORT).show();
                favbycheck(position, myDbManager)
                (context as MainActivity).foo()
                context.elinrecc()


                dialog.dismiss()
            }

            true

        }


    }
    override fun getItemCount(): Int {
        return mainArray.size
    }
    fun ifSearchClear(itemsList: List<ListItem>) {
        mainArray.addAll(itemsList)
        mainArray.clear()
        notifyDataSetChanged()
    }

    fun updateAdapter(itemsList: List<ListItem>) {
        mainArray.clear()
        mainArray.addAll(itemsList)
        notifyDataSetChanged()
    }
    fun favbycheck(position: Int, dbManager: MyDbManager){
        dbManager.openDb()
        myDbManager.updateBut(mainArray[position].id, "1")
        mainArray.removeAt(position)
        notifyDataSetChanged()
        dbManager.closeDb()
    }
    fun removebycheck(position: Int, dbManager: MyDbManager){
        dbManager.openDb()
        dbManager.deleteOneRow(mainArray[position].id)
        mainArray.removeAt(position)
        notifyDataSetChanged()
        dbManager.closeDb()
    }
    fun removeSwipe(position: Int, dbManager: MyDbManager) {
        dbManager.deleteOneRow(mainArray[position].id)
        mainArray.removeAt(position)
        notifyItemRangeChanged(0, mainArray.size)
        notifyItemRemoved(position)
    }


}


