package com.example.libraryhub.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryhub.R
import com.example.libraryhub.model.Subscription

class AdapterSubscription(private val dataSet : ArrayList<Subscription>) : RecyclerView.Adapter<AdapterSubscription.ViewHolder>() {
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val time : TextView
        val discount : TextView
        val checkBox : CheckBox
        val oldPrice : TextView
        val price : TextView
        init {
            time = view.findViewById(R.id.subscriptionTime)
            discount = view.findViewById(R.id.subscriptionDiscount)
            checkBox = view.findViewById(R.id.subscriptionCheckBox)
            oldPrice = view.findViewById(R.id.subscriptionOldPrice)
            price = view.findViewById(R.id.subscriptionPrice)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.subscription_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.time.text = "${dataSet[position].time} MONTHS"
        holder.discount.text = "${dataSet[position].discount}% OFF"
        holder.oldPrice.text = "${dataSet[position].oldPrice}VND"
        holder.oldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        val newPrice = dataSet[position].oldPrice * dataSet[position].discount / 100
        holder.price.text = "${newPrice}VND"

    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}