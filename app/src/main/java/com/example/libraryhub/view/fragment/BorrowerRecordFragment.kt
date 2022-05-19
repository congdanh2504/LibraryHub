package com.example.libraryhub.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryhub.adapter.RecordAdapter
import com.example.libraryhub.databinding.FragmentBorrowerRecordBinding
import com.example.libraryhub.viewmodel.AdminViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BorrowerRecordFragment : Fragment() {
    private lateinit var borrowerRecordBinding: FragmentBorrowerRecordBinding
    private val adminViewModel: AdminViewModel by activityViewModels()
    private lateinit var adapter: RecordAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        borrowerRecordBinding = FragmentBorrowerRecordBinding.inflate(inflater, container, false)
        initRecyclerView()
        initObserver()
        initActions()
        return borrowerRecordBinding.root
    }

    private fun initRecyclerView() {
        adapter = RecordAdapter(requireContext())
        adminViewModel.getAllRecord()
        borrowerRecordBinding.recordRecycler.layoutManager = LinearLayoutManager(context)
        borrowerRecordBinding.recordRecycler.adapter = adapter
    }

    private fun initObserver() {
        adminViewModel.records.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                borrowerRecordBinding.recordRecycler.visibility = View.VISIBLE
                borrowerRecordBinding.emptyImage.visibility = View.GONE
                borrowerRecordBinding.emptyText.visibility = View.GONE
                adapter.setRecords(it)
            } else {
                borrowerRecordBinding.recordRecycler.visibility = View.GONE
                borrowerRecordBinding.emptyImage.visibility = View.VISIBLE
                borrowerRecordBinding.emptyText.visibility = View.VISIBLE
            }

        }
    }

    private fun initActions() {
        borrowerRecordBinding.swipeToRefresh.setOnRefreshListener {
            adminViewModel.getAllRecord()
            borrowerRecordBinding.swipeToRefresh.isRefreshing = false
        }
    }
}