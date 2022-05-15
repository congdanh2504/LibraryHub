package com.example.libraryhub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryhub.R
import com.example.libraryhub.model.Book
import com.squareup.picasso.Picasso

class AdapterBook(private val onBookClick: (Book) -> Unit) :
    RecyclerView.Adapter<AdapterBook.ViewHolder>() {
    private var oldList: List<Book> = listOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.cardName)
        val image: ImageView = view.findViewById(R.id.cardThubnail)
        val author: TextView = view.findViewById(R.id.cardAuthor)
        val card: CardView = view.findViewById(R.id.card_layout)
    }

    inner class MyDiffUtil(
        private val newList: List<Book>,
        private val oldList: List<Book>
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = oldList[position].name
        holder.author.text = oldList[position].author
        Picasso.get()
            .load(oldList[position].picture)
            .placeholder(R.drawable.placeholdeimage)
            .into(holder.image)
        holder.card.setOnClickListener {
            onBookClick(oldList[position])
        }
    }

    override fun getItemCount(): Int {
        return oldList.size
    }

    fun setBook(newList: List<Book>) {
        val diffUtil = MyDiffUtil(oldList = oldList, newList = newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldList = newList
        diffResult.dispatchUpdatesTo(this)
    }
}