package com.example.dacs3.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryhub.R
import com.example.libraryhub.model.Comment
import com.squareup.picasso.Picasso
import java.util.zip.Inflater

class AdapterComment(private val dataSet : ArrayList<Comment>) : RecyclerView.Adapter<AdapterComment.CommentViewHolder>() {
    class CommentViewHolder(view : View) : RecyclerView.ViewHolder(view){
//        val avatar : ImageView
//        val name : TextView
//        val comment : TextView
//        val rate : RatingBar
//        init {
//            avatar = view.findViewById(R.id.userAvatar)
//            name = view.findViewById(R.id.username)
//            comment = view.findViewById(R.id.comment)
//            rate = view.findViewById(R.id.commentRate)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): AdapterComment.CommentViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.comment_item, parent, false)
//
//
//        return AdapterComment.CommentViewHolder(view)
//    }

//    override fun onBindViewHolder(holder: AdapterComment.CommentViewHolder, position: Int) {
//        holder.name.text = dataSet[position].name
//        holder.comment.text = dataSet[position].comment
//        holder.rate.rating = dataSet[position].rate.toFloat()
//        Picasso.get()
//            .load(dataSet[position].avatar)
//            .placeholder(R.drawable.ic_launcher_background)
//            .into(holder.avatar)
//    }
//
//    override fun getItemCount(): Int {
//        return dataSet.size
//    }