package com.example.notes.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.ListActivity.AddNotesActivity
import com.example.notes.ListActivity.EditActivity
import com.example.notes.R


class MainAdapterK(private val context: Context) :
    RecyclerView.Adapter<MainAdapterK.ViewHolder>() {
    private val mainArray: MutableList<ListItem>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(mainArray[position].getTitle(),mainArray[position].getContent())
      //  holder.idTitle.setText()
        holder.itemView.setOnClickListener { v ->
            val context = v.context
            val intent = Intent(context, EditActivity::class.java)
            //intent.putExtra("", String())

            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return mainArray.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val idTitle: TextView
        private val idContent: TextView
        fun setData(title: String?,content:String? ) {
            idTitle.text = title
            idContent.text = content
        }

        init {
            idTitle = itemView.findViewById(R.id.Id_Title)
            idContent = itemView.findViewById(R.id.Id_Content)

        }
    }

    fun updateAdapter(newList: List<ListItem>?) {
        mainArray.clear()
        mainArray.addAll(newList!!)
        notifyDataSetChanged()
    }

    init {
        mainArray = ArrayList()
    }


}


