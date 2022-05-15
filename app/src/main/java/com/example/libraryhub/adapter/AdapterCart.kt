package com.example.libraryhub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryhub.R
import com.example.libraryhub.model.Book
import com.squareup.picasso.Picasso

class AdapterCart(private val dataSet : ArrayList<Book>) : RecyclerView.Adapter<AdapterCart.ViewHolder>() {
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val title : TextView
        val image : ImageView
        val location : TextView
        val price : TextView
        val checkBox : CheckBox
        val delete : ImageView
        init{
            title = view.findViewById(R.id.cartItemTitle)
            image = view.findViewById(R.id.cartItemImage)
            location = view.findViewById(R.id.cartItemLocation)
            price = view.findViewById(R.id.cartItemPrice)
            checkBox = view.findViewById(R.id.cartItemSelect)
            delete = view.findViewById(R.id.cartItemDelete)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = dataSet[position].name
        holder.location.text = dataSet[position].location.toString()
        holder.price.text = "${dataSet[position].price}VND"
        Picasso.get()
            .load(dataSet[position].picture)
            .placeholder(R.drawable.placeholdeimage)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}