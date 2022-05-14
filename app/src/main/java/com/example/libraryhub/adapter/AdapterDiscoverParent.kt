package com.example.libraryhub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryhub.R
import com.example.libraryhub.model.DiscoverParent

class AdapterDiscoverParent(private val dataSet : List<DiscoverParent>) :RecyclerView.Adapter<AdapterDiscoverParent.ParentViewHolder>() {
    class ParentViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val parentTitle : TextView
        var ChildList : RecyclerView
        init {
            parentTitle = view.findViewById(R.id.parentTitle)
            ChildList = view.findViewById(R.id.RecyclerChildList)
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterDiscoverParent.ParentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.discover_parent_item,parent,false)
        return ParentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterDiscoverParent.ParentViewHolder, position: Int) {
        holder.parentTitle.text = dataSet[position].title
        val childMemberAdapter = AdapterDiscoverChildren(dataSet[position].childItems)
        holder.ChildList.layoutManager = LinearLayoutManager(holder.ChildList.context,LinearLayoutManager.HORIZONTAL,false)
        holder.ChildList.adapter = childMemberAdapter
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}