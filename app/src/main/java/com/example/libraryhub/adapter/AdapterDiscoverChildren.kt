package com.example.libraryhub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryhub.R
import com.example.libraryhub.model.Book
import com.squareup.picasso.Picasso

class AdapterDiscoverChildren(private val dataSet : List<Book>) : RecyclerView.Adapter<AdapterDiscoverChildren.DiscoverViewHolder>() {
    class DiscoverViewHolder(view : View): RecyclerView.ViewHolder(view){
        val thumbnail : ImageView
        val title : TextView
        val author : TextView
        init {
            thumbnail = view.findViewById(R.id.img_child_item)
            title = view.findViewById(R.id.child_item_title)
            author = view.findViewById(R.id.child_item_author)
        }

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterDiscoverChildren.DiscoverViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.discover_children_item,parent,false)
        return DiscoverViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterDiscoverChildren.DiscoverViewHolder, position: Int) {
        Picasso.get()
            .load(dataSet[position].picture)
            .placeholder(R.drawable.placeholdeimage)
            .into(holder.thumbnail)
        holder.title.text = dataSet[position].name
        holder.author.text = dataSet[position].author
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}