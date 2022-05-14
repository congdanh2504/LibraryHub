package com.example.libraryhub.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryhub.adapter.AdapterDiscoverParent
import com.example.libraryhub.databinding.FragmentDiscoverBinding
import com.example.libraryhub.model.*
import com.example.libraryhub.utils.AppPreferences
import com.example.libraryhub.viewmodel.DiscoverViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverFragment : Fragment() {
    private lateinit var disBinding: FragmentDiscoverBinding
    private val discoverViewModel: DiscoverViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        disBinding = FragmentDiscoverBinding.inflate(inflater, container, false)
        disBinding.DiscoverRecyclerView.layoutManager = LinearLayoutManager(context)

        discoverViewModel.discover.observe(viewLifecycleOwner) {
            val data: List<DiscoverParent> = listOf(DiscoverParent("Top 10 highest rate books", it[0]),
                DiscoverParent("Top 10 highest borrowed number books", it[1]),
                DiscoverParent("Top 10 highest review books", it[2]))
//            data.add(DiscoverParent("Top 10 highest rate books", it[0]))
//            data.add(DiscoverParent("Top 10 highest borrowed number books", it[1]))
//            data.add(DiscoverParent("Top 10 highest review books", it[2]))
            disBinding.DiscoverRecyclerView.adapter = AdapterDiscoverParent(data)
        }

//        for (i in 1..10) {
//            childData.add(Book(
//                "Danh",
//                4.5,
//                3,
//                Category("History", "laksjd"),
//                "asdasd",
//                Location(1,2,3),
//                "dasd",
//                "https://product.hstatic.net/200000343865/product/6_90462943d6e348c8a807fddc3f0d688b_master.jpg",
//                19,
//                2020,
//                "Danh",
//                10,
//                listOf(Review("asd", "asd", 5, AppPreferences.user!!)),
//                "available"
//            ))
//        }

//        5 children field
//        for (i in 1..3) {
//
//        }

        return disBinding.root
    }
}