package com.example.libraryhub.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.libraryhub.databinding.FragmentDiscoverBinding

class DiscoverFragment : Fragment() {
    private lateinit var disBinding: FragmentDiscoverBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        disBinding = FragmentDiscoverBinding.inflate(inflater, container, false)
//        disBinding.DiscoverRecyclerView.layoutManager = LinearLayoutManager(context)
//        val data = ArrayList<DiscoverParent>()
//        val childData = ArrayList<Book>()
//        for (i in 1..5) {
//            childData.add(Book("123","this is the title","https://product.hstatic.net/200000343865/product/6_90462943d6e348c8a807fddc3f0d688b_master.jpg","this is description","this is author","publisher",123456,
//                Location("1","2","3")
//            ))
//        }
//
////        5 children field
//        for (i in 1..5) {
//            data.add(DiscoverParent("title "+i,childData))
//        }
//        disBinding.DiscoverRecyclerView.adapter = AdapterDiscoverParent(data)
        return disBinding.root
    }
}