package com.example.libraryhub.adapter

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryhub.R
import com.example.libraryhub.model.RequestedBook
import com.squareup.picasso.Picasso

class RequestedAdapter(private val context: Context, private val onDelete: (bookId: String) -> Unit) :
    RecyclerView.Adapter<RequestedAdapter.ViewHolder>() {
    private var oldList: List<RequestedBook> = listOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val image: ImageView = view.findViewById(R.id.picture)
        val author: TextView = view.findViewById(R.id.author)
        val isAccepted: TextView = view.findViewById(R.id.isAccepted)
        val remove: ImageView = view.findViewById(R.id.remove)
    }

    inner class MyDiffUtil(
        private val newList: List<RequestedBook>,
        private val oldList: List<RequestedBook>
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.requested_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = oldList[position].name
        holder.author.text = oldList[position].author
        Picasso.get()
            .load(oldList[position].picture)
            .placeholder(R.drawable.placeholdeimage)
            .into(holder.image)
        if (oldList[position].isAccepted) {
            holder.isAccepted.text = "Accepted"
            holder.isAccepted.setTextColor(Color.GREEN)
        }
        holder.remove.setOnClickListener {
            val alertDialog = AlertDialog.Builder(context)
                .setTitle("Borrowing confirm")
                .setMessage("Do you want to return?")
                .setCancelable(false)
                .setPositiveButton(
                    "Yes"
                ) { _, _ ->
                    onDelete(oldList[position]._id)
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.cancel()
                }
                .create()
            alertDialog.show()
        }
    }

    override fun getItemCount(): Int {
        return oldList.size
    }

    fun setBook(newList: List<RequestedBook>) {
        val diffUtil = MyDiffUtil(oldList = oldList, newList = newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldList = newList
        diffResult.dispatchUpdatesTo(this)
    }
}