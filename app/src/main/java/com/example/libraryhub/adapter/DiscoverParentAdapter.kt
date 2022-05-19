package com.example.libraryhub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryhub.R
import com.example.libraryhub.model.Book
import com.example.libraryhub.model.DiscoverParent

class DiscoverParentAdapter(
    private val dataSet: List<DiscoverParent>,
    private val onBookClick: (Book) -> Unit
) : RecyclerView.Adapter<DiscoverParentAdapter.ParentViewHolder>() {
    class ParentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val parentTitle: TextView = view.findViewById(R.id.parentTitle)
        var childList: RecyclerView = view.findViewById(R.id.RecyclerChildList)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ParentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.discover_parent_item, parent, false)
        return ParentViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiscoverParentAdapter.ParentViewHolder, position: Int) {
        holder.parentTitle.text = dataSet[position].title
        val childMemberAdapter = DiscoverChildrenAdapter(dataSet[position].childItems, onBookClick)
        holder.childList.layoutManager =
            LinearLayoutManager(holder.childList.context, LinearLayoutManager.HORIZONTAL, false)
        holder.childList.adapter = childMemberAdapter
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}