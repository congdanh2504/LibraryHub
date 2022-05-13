package com.example.libraryhub.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.libraryhub.adapter.AdapterSearch
import com.example.libraryhub.databinding.FragmentSearchBinding
import com.example.libraryhub.model.Category
import com.example.libraryhub.viewmodel.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var searchBinding: FragmentSearchBinding
    private val categoryViewModel: CategoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        searchBinding = FragmentSearchBinding.inflate(inflater, container, false)
        searchBinding.SearchRecyclerView.layoutManager = GridLayoutManager(context,2)

        try {
            categoryViewModel.categories.observe(viewLifecycleOwner) {
                searchBinding.SearchRecyclerView.adapter = AdapterSearch(it)
            }
        } catch (e: Exception) {
            Log.d("AAA", "${e.message}")
        }



        return searchBinding.root
    }

}