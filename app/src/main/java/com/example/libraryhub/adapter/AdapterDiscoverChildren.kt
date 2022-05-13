package com.example.dacs3.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryhub.R
import com.example.libraryhub.model.Book
import com.example.libraryhub.model.DiscoverParent
import com.squareup.picasso.Picasso

class AdapterDiscoverChildren(private val dataSet : ArrayList<Book>) : RecyclerView.Adapter<AdapterDiscoverChildren.DiscoverViewHolder>() {
    class DiscoverViewHolder(view : View): RecyclerView.ViewHolder(view){
//        val thumbnail : ImageView
//        val title : TextView
//        val author : TextView
//        init {
//            thumbnail = view.findViewById(R.id.img_child_item)
//            title = view.findViewById(R.id.child_item_title)
//            author = view.findViewById(R.id.child_item_author)
//        }

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterDiscoverChildren.DiscoverViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.discover_children_item,parent,false)
        return DiscoverViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterDiscoverChildren.DiscoverViewHolder, position: Int) {
//        Picasso.get()
//            .load(dataSet[position].thumbnail)
//            .placeholder(R.drawable.ic_launcher_background)
//            .into(holder.thumbnail)
//        holder.title.text = dataSet[position].title
//        holder.author.text = dataSet[position].author
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}