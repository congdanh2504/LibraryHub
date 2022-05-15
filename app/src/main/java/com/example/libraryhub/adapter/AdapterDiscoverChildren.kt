package com.example.libraryhub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryhub.R
import com.example.libraryhub.model.Book
import com.squareup.picasso.Picasso

class AdapterDiscoverChildren(private val dataSet : List<Book>, private val onBookClick: (Book) -> Unit) : RecyclerView.Adapter<AdapterDiscoverChildren.DiscoverViewHolder>() {
    class DiscoverViewHolder(view : View): RecyclerView.ViewHolder(view){
        val thumbnail : ImageView = view.findViewById(R.id.img_child_item)
        val title : TextView = view.findViewById(R.id.child_item_title)
        val author : TextView = view.findViewById(R.id.child_item_author)
        val card: CardView = view.findViewById(R.id.card_layout)
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
        holder.card.setOnClickListener {
            onBookClick(dataSet[position])
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}