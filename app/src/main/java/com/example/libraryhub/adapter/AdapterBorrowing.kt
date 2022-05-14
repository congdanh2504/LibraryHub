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

class AdapterBorrowing(private val dataSet: ArrayList<Book>) :
    RecyclerView.Adapter<AdapterBorrowing.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView
        val thubnail: ImageView
        init {
            title = view.findViewById(R.id.cardTitle)
            thubnail = view.findViewById(R.id.cardThubnail)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.detail_card, viewGroup, false)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        Picasso.get()
            .load(dataSet[position].picture)
            .placeholder(R.drawable.ic_launcher_background)
            .into(viewHolder.thubnail)

        viewHolder.title.text = dataSet[position].name
    }

    override fun getItemCount() = dataSet.size

}