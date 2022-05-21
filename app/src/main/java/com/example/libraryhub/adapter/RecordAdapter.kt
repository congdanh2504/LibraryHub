package com.example.libraryhub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryhub.R
import com.example.libraryhub.model.BorrowerRecord
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class RecordAdapter(private val context: Context) :
    RecyclerView.Adapter<RecordAdapter.ViewHolder>() {
    private var oldList: List<BorrowerRecord> = listOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val username: TextView = view.findViewById(R.id.username)
        val status: TextView = view.findViewById(R.id.status)
        val borrowDate: TextView = view.findViewById(R.id.createdDate)
        val returnDate: TextView = view.findViewById(R.id.returnDate)
        val borrowerRecycler: RecyclerView = view.findViewById(R.id.borrowerRecycler)
        val avatar: ShapeableImageView = view.findViewById(R.id.avatar)
    }

    inner class MyDiffUtil(
        private val newList: List<BorrowerRecord>,
        private val oldList: List<BorrowerRecord>
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.record_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.username.text = oldList[position].user.username
        holder.status.text = oldList[position].status
        val dateFormatter = SimpleDateFormat("EEE, MMM d, yyyy HH:mm", Locale.getDefault())
        holder.borrowDate.text = dateFormatter.format(oldList[position].createdDate)
        holder.returnDate.text = dateFormatter.format(oldList[position].returnDate)
        Picasso.get()
            .load(oldList[position].user.picture)
            .placeholder(R.drawable.placeholdeimage)
            .into(holder.avatar)
        holder.borrowerRecycler.layoutManager = CustomLinearLayoutManager(context)
        val adapter = BorrowerAdapter()
        holder.borrowerRecycler.adapter = adapter
        adapter.setBooks(oldList[position].books)
    }

    override fun getItemCount(): Int {
        return oldList.size
    }

    fun setRecords(newList: List<BorrowerRecord>) {
        val diffUtil = MyDiffUtil(oldList = oldList, newList = newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    inner class CustomLinearLayoutManager(context: Context) : LinearLayoutManager(context) {
        override fun canScrollVertically(): Boolean {
            return false
        }

        override fun canScrollHorizontally(): Boolean {
            return false
        }
    }
}