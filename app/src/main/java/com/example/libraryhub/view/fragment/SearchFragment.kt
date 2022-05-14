package com.example.libraryhub.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.libraryhub.R
import com.example.libraryhub.adapter.AdapterSearch
import com.example.libraryhub.databinding.FragmentSearchBinding
import com.example.libraryhub.model.Category
import com.example.libraryhub.viewmodel.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var searchBinding: FragmentSearchBinding
    private val categoryViewModel: CategoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        searchBinding = FragmentSearchBinding.inflate(inflater, container, false)
        searchBinding.SearchRecyclerView.layoutManager = GridLayoutManager(context,2)

        categoryViewModel.categories.observe(viewLifecycleOwner) {
            searchBinding.SearchRecyclerView.adapter = AdapterSearch(it, onCategoryClick)
        }

        return searchBinding.root
    }

    private val onCategoryClick: (categoryId: String) -> Unit = { categoryId ->
        val action = SearchFragmentDirections.actionSearchFragmentToBookListFragment(categoryId)
        findNavController().navigate(action)
    }
}