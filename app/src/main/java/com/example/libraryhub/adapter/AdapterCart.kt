package com.example.libraryhub.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryhub.R
import com.example.libraryhub.model.Book
import com.example.libraryhub.model.CartBook
import com.example.libraryhub.utils.AppPreferences
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class AdapterCart() :
    RecyclerView.Adapter<AdapterCart.ViewHolder>() {
    private var oldList: ArrayList<CartBook> = arrayListOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val image: ImageView = view.findViewById(R.id.image)
        val author: TextView = view.findViewById(R.id.author)
        val quantity: TextView = view.findViewById(R.id.quantity)
        val remove: ImageView = view.findViewById(R.id.remove)
    }

    inner class MyDiffUtil(
        private val newList: ArrayList<CartBook>,
        private val oldList: ArrayList<CartBook>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition]._id == newList[newItemPosition]._id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList == newList
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = oldList[position]
        holder.name.text = book.name
        holder.author.text = book.author
        holder.quantity.text = "x${book.quantity}"
        Picasso.get()
            .load(book.picture)
            .placeholder(R.drawable.placeholdeimage)
            .into(holder.image)
        holder.remove.setOnClickListener {
            val tempBooks = AppPreferences.cart!!
            val indexOfBook = tempBooks.indexOf(book)
            if (book.quantity > 1) {
                tempBooks[indexOfBook].quantity--
                holder.quantity.text = "x${book.quantity-1}"
            } else {
                tempBooks.remove(book)
                setBooks(tempBooks)
            }
            AppPreferences.cart = tempBooks
            Snackbar.make(holder.itemView, """Remove "${book.name}" from the cart""", Snackbar.LENGTH_LONG).setAction("Undo"
            ) {
                if (book.quantity > 1) {
                    tempBooks[indexOfBook].quantity++
                    holder.quantity.text = "x${book.quantity}"
                } else {
                    tempBooks.add(book)
                    notifyDataSetChanged()
                }
                AppPreferences.cart = tempBooks
            }.show()
        }
    }

    override fun getItemCount(): Int {
        return oldList.size
    }

    fun setBooks(newList: ArrayList<CartBook>) {
        val diffUtil = MyDiffUtil(oldList = oldList, newList = newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldList = newList
        diffResult.dispatchUpdatesTo(this)
    }
}