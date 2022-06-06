package com.example.libraryhub.adapter

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryhub.R
import com.example.libraryhub.model.Notification
import java.text.SimpleDateFormat
import java.util.*

class NotificationAdapter: RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
    private var oldList: List<Notification> = arrayListOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val message: TextView = view.findViewById(R.id.message)
        val createdDate: TextView = view.findViewById(R.id.createdDate)
    }

    inner class MyDiffUtil(
        private val newList: List<Notification>,
        private val oldList: List<Notification>
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.message.text = oldList[position].message
        val dateFormatter = SimpleDateFormat("EEE, MMM d, yyyy HH:mm", Locale.getDefault())
        holder.createdDate.text = dateFormatter.format(oldList[position].createdDate)
        if (!oldList[position].isSeen) holder.message.typeface = Typeface.DEFAULT_BOLD
    }

    override fun getItemCount(): Int {
        return oldList.size
    }

    fun setNotifications(newList: List<Notification>) {
        val diffUtil = MyDiffUtil(oldList = oldList, newList = newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldList = newList
        diffResult.dispatchUpdatesTo(this)
    }
}