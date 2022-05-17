package com.example.libraryhub.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryhub.R
import com.example.libraryhub.model.Package

class AdapterPackage(private val dataSet: List<Package>,private val onBuy: (String) -> Unit) :
    RecyclerView.Adapter<AdapterPackage.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val price: TextView = view.findViewById(R.id.price)
        val time: TextView = view.findViewById(R.id.time)
        val benefit1: TextView = view.findViewById(R.id.benefit1)
        val benefit2: TextView = view.findViewById(R.id.benefit2)
        val benefit3: TextView = view.findViewById(R.id.benefit3)
        val card: CardView = view.findViewById(R.id.card)
        val selectPlanButton: Button = view.findViewById(R.id.selectPlanButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.package_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val color = arrayOf("#27856a", "#8d67ab")
        holder.time.text = "${dataSet[position].time} MONTHS"
        holder.price.text = "${dataSet[position].price} VND"
        holder.name.text = dataSet[position].name
        holder.card.background.setTint(Color.parseColor(color[position % color.size]))
        holder.selectPlanButton.background.setTint(Color.parseColor(color[position % color.size]))
        holder.benefit1.text = dataSet[position].benefit
        holder.benefit2.text = "${dataSet[position].booksPerLoan} per loan"
        holder.benefit3.text = "Can borrow in ${dataSet[position].borrowDays} days"
        holder.selectPlanButton.setOnClickListener {
            onBuy(dataSet[position]._id)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}