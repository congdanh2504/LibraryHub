package com.example.libraryhub.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryhub.R
import com.example.libraryhub.adapter.AdapterBorrow
import com.example.libraryhub.databinding.FragmentBorrowingBinding
import com.example.libraryhub.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class BorrowingFragment : Fragment() {
    private lateinit var borrowingBinding: FragmentBorrowingBinding
    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var adapter: AdapterBorrow

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        borrowingBinding = FragmentBorrowingBinding.inflate(inflater, container, false)
        adapter = AdapterBorrow()
        borrowingBinding.borrowingRecycler.layoutManager = LinearLayoutManager(context)
        borrowingBinding.borrowingRecycler.adapter = adapter
        homeViewModel.getBorrowingBooks()

        homeViewModel.borrowerRecord.observe(viewLifecycleOwner) {
            borrowingBinding.borrowRecord.visibility = View.VISIBLE
            borrowingBinding.borrowingRecycler.visibility = View.VISIBLE
            borrowingBinding.emptyImage.visibility = View.GONE
            borrowingBinding.emptyText.visibility = View.GONE
            borrowingBinding.status.text = it.status
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            borrowingBinding.createdDate.text = dateFormatter.format(it.createdDate)
            borrowingBinding.returnDate.text = dateFormatter.format(it.returnDate)
            adapter.setBooks(it.books)
        }

        return borrowingBinding.root
    }

}