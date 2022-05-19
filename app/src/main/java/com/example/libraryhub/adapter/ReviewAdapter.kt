package com.example.libraryhub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryhub.R
import com.example.libraryhub.model.Review
import com.squareup.picasso.Picasso

class ReviewAdapter() :
    RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    private var oldList: List<Review> = listOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val username: TextView = view.findViewById(R.id.username)
        val image: ImageView = view.findViewById(R.id.avatar)
        val comment: TextView = view.findViewById(R.id.comment)
        val ratingBar: RatingBar = view.findViewById(R.id.rate)
    }

    inner class MyDiffUtil(
        private val newList: List<Review>,
        private val oldList: List<Review>
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.review_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.username.text = oldList[position].user.username
        holder.comment.text = oldList[position].comment
        holder.ratingBar.rating = oldList[position].rate.toFloat()
        Picasso.get()
            .load(oldList[position].user.picture)
            .placeholder(R.drawable.placeholdeimage)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return oldList.size
    }

    fun setReviews(newList: List<Review>) {
        val diffUtil = MyDiffUtil(oldList = oldList, newList = newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldList = newList
        diffResult.dispatchUpdatesTo(this)
    }
}