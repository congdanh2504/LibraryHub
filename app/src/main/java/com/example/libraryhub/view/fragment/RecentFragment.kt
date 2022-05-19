package com.example.libraryhub.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryhub.adapter.BookAdapter
import com.example.libraryhub.databinding.FragmentRecentBinding
import com.example.libraryhub.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecentFragment : Fragment() {
    private lateinit var recentBinding: FragmentRecentBinding
    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var adapter: BookAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        recentBinding = FragmentRecentBinding.inflate(inflater, container, false)
        initActions()
        adapter = BookAdapter{}
        recentBinding.recentRecycler.layoutManager = LinearLayoutManager(context)
        recentBinding.recentRecycler.adapter = adapter
        homeViewModel.getRecentBooks()

        homeViewModel.recentBooks.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                recentBinding.recentRecycler.visibility = View.VISIBLE
                recentBinding.emptyImage.visibility = View.GONE
                recentBinding.emptyText.visibility = View.GONE
                adapter.setBook(it)
            } else {
                recentBinding.recentRecycler.visibility = View.GONE
                recentBinding.emptyImage.visibility = View.VISIBLE
                recentBinding.emptyText.visibility = View.VISIBLE
            }
        }

        return recentBinding.root
    }

    private fun initActions() {
        recentBinding.swipeToRefresh.setOnRefreshListener {
            homeViewModel.getRecentBooks()
            recentBinding.swipeToRefresh.isRefreshing = false
        }
    }

}