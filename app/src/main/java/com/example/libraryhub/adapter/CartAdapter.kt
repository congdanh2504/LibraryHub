package com.example.libraryhub.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryhub.R
import com.example.libraryhub.model.CartBook
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class CartAdapter(private val onBookChange: (ArrayList<CartBook>) -> Unit) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private var oldList: ArrayList<CartBook> = arrayListOf()
    private lateinit var cloneBook: CartBook

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val image: ImageView = view.findViewById(R.id.image)
        val author: TextView = view.findViewById(R.id.author)
        val quantity: TextView = view.findViewById(R.id.quantity)
        val remove: ImageView = view.findViewById(R.id.remove)
        val select: CheckBox = view.findViewById(R.id.select)
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
        if (book.isSelected) holder.select.isChecked = true
        Picasso.get()
            .load(book.picture)
            .placeholder(R.drawable.placeholdeimage)
            .into(holder.image)
        holder.select.setOnClickListener {
            oldList[position].isSelected = !oldList[position].isSelected
            onBookChange(oldList)
        }

        holder.remove.setOnClickListener {
            cloneBook = book.copy()
            if (oldList[position].quantity > 1) {
                oldList[position].quantity--
            } else {
                oldList.remove(book)
            }
            Snackbar.make(
                holder.itemView,
                """Remove "${book.name}" from the cart""",
                Snackbar.LENGTH_LONG
            ).setAction("Undo") {
                if (cloneBook.quantity > 1) {
                    oldList[position].quantity++
                } else {
                    oldList.add(cloneBook)
                }
                onBookChange(oldList)
            }.show()
            onBookChange(oldList)
        }
    }

    override fun getItemCount(): Int {
        return oldList.size
    }

    fun getSelectedBooks(): ArrayList<CartBook> {
        val selectedBooks: ArrayList<CartBook> = arrayListOf()
        oldList.forEach {
            if (it.isSelected) selectedBooks.add(it)
        }
        return selectedBooks
    }

    fun deleteSelectedBooks() {
        val newList = oldList.filter { !it.isSelected }
        setBooks(newList as ArrayList<CartBook>)
        onBookChange(newList)
    }

    fun setBooks(newList: ArrayList<CartBook>) {
        oldList = newList
        notifyDataSetChanged()
    }
}