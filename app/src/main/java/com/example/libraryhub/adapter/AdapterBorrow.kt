package com.example.libraryhub.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryhub.R
import com.example.libraryhub.model.CartBook
import com.example.libraryhub.model.QuantityBook
import com.squareup.picasso.Picasso

class AdapterBorrow() :
    RecyclerView.Adapter<AdapterBorrow.ViewHolder>() {
    private var oldList: List<QuantityBook> = arrayListOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val image: ImageView = view.findViewById(R.id.image)
        val author: TextView = view.findViewById(R.id.author)
        val quantity: TextView = view.findViewById(R.id.quantity)
    }

    inner class MyDiffUtil(
        private val newList: List<QuantityBook>,
        private val oldList: List<QuantityBook>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].book._id == newList[newItemPosition].book._id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList == newList
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.borrower_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = oldList[position]
        holder.name.text = book.book.name
        holder.author.text = book.book.author
        holder.quantity.text = "x${book.quantity}"
        Picasso.get()
            .load(book.book.picture)
            .placeholder(R.drawable.placeholdeimage)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return oldList.size
    }

    fun setBooks(newList: List<QuantityBook>) {
        val diffUtil = MyDiffUtil(oldList = oldList, newList = newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldList = newList
        diffResult.dispatchUpdatesTo(this)
    }
}