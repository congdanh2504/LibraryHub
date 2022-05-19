package com.example.libraryhub.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryhub.R
import com.example.libraryhub.model.Category
import com.squareup.picasso.Picasso

class SearchAdapter(private val dataSet : List<Category>, private val onCategoryClick: (Category) -> Unit) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val title : TextView
        val image : ImageView
        val card : CardView
        init {
            title = view.findViewById(R.id.SearchChildrenTitle)
            image = view.findViewById(R.id.SearchChildrenImage)
            card = view.findViewById(R.id.SearchCard)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item,parent,false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val color = arrayOf("#27856a","#1e3264","#8d67ab","#e8115b","#1e3264","#148a08","#a56752","#477d95","#608108","#0d73ec","#ff4632","#8c1932","#1c1e21")
        holder.title.text = dataSet[position].name
        Picasso.get()
            .load(dataSet[position].picture)
            .placeholder(R.drawable.placeholdeimage)
            .into(holder.image)
        holder.card.setOnClickListener {
            onCategoryClick(dataSet[position])
        }
        holder.card.background.setTint(Color.parseColor(color[position%13]))
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}