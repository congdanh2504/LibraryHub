package com.example.libraryhub.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryhub.adapter.RecordAdapter
import com.example.libraryhub.adapter.RequestedManagerAdapter
import com.example.libraryhub.databinding.FragmentRequestedBooksBinding
import com.example.libraryhub.viewmodel.AdminViewModel

class RequestedBooksFragment : Fragment() {
    private lateinit var requestedBooksBinding: FragmentRequestedBooksBinding
    private val adminViewModel: AdminViewModel by activityViewModels()
    private lateinit var adapter: RequestedManagerAdapter
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requestedBooksBinding = FragmentRequestedBooksBinding.inflate(inflater, container, false)
        initRecyclerView()
        initObserver()
        initActions()
        return requestedBooksBinding.root
    }

    private fun initRecyclerView() {
        adapter = RequestedManagerAdapter(requireContext(), onAccept)
        adminViewModel.getRequestedBooks()
        requestedBooksBinding.requestedRecycler.layoutManager = LinearLayoutManager(context)
        requestedBooksBinding.requestedRecycler.adapter = adapter
    }

    private fun initObserver() {
        adminViewModel.requestedBooks.observe(viewLifecycleOwner) {
            isLoading = false
            requestedBooksBinding.progressBar.visibility = View.GONE
            if (it.isNotEmpty()) {
                requestedBooksBinding.requestedRecycler.visibility = View.VISIBLE
                requestedBooksBinding.emptyImage.visibility = View.GONE
                requestedBooksBinding.emptyText.visibility = View.GONE
                adapter.setBook(it)
            } else {
                requestedBooksBinding.requestedRecycler.visibility = View.GONE
                requestedBooksBinding.emptyImage.visibility = View.VISIBLE
                requestedBooksBinding.emptyText.visibility = View.VISIBLE
            }

        }
    }

    private fun initActions() {
        requestedBooksBinding.swipeToRefresh.setOnRefreshListener {
            adminViewModel.refreshRequestedBooks()
            requestedBooksBinding.swipeToRefresh.isRefreshing = false
        }
        requestedBooksBinding.requestedRecycler.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE && !isLoading) {
                    requestedBooksBinding.progressBar.visibility = View.VISIBLE
                    isLoading = true
                    adminViewModel.getRequestedBooks()
                }
            }
        })
    }

    private val onAccept: (bookId: String) -> Unit = {
        adminViewModel.acceptRequest(it)
    }
}