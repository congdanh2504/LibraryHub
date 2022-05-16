package com.example.libraryhub.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryhub.adapter.AdapterBook
import com.example.libraryhub.adapter.AdapterSearch
import com.example.libraryhub.databinding.FragmentSearchBinding
import com.example.libraryhub.model.Book
import com.example.libraryhub.model.Category
import com.example.libraryhub.viewmodel.SearchViewModel
import com.jakewharton.rxbinding.widget.RxSearchView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var searchBinding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by activityViewModels()
    private lateinit var adapter: AdapterBook

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        searchBinding = FragmentSearchBinding.inflate(inflater, container, false)
        initRecyclerView()
        initObserver()
        initActions()
        return searchBinding.root
    }

    private fun initRecyclerView() {
        searchBinding.categoriesRecycler.layoutManager = GridLayoutManager(context,2)
        searchBinding.searchRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = AdapterBook(onBookClick)
        searchBinding.searchRecyclerView.adapter = adapter
    }

    private fun initObserver() {
        searchViewModel.categories.observe(viewLifecycleOwner) {
            searchBinding.categoriesRecycler.adapter = AdapterSearch(it, onCategoryClick)
        }
        searchViewModel.searchingBook.observe(viewLifecycleOwner) {
            adapter.setBook(it)
        }
    }

    private val onCategoryClick: (category: Category) -> Unit = { category ->
        val action = SearchFragmentDirections.actionSearchFragmentToBookListFragment(category)
        findNavController().navigate(action)
    }

    private val onBookClick: (book: Book) -> Unit = { book ->
        val action = SearchFragmentDirections.actionSearchFragmentToBookDetailFragment(book)
        findNavController().navigate(action)
    }

    private fun initActions() {
        RxSearchView.queryTextChanges(searchBinding.search)
            .debounce(1, TimeUnit.SECONDS) // stream will go down after 1 second inactivity of user
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t ->
                val query = t.toString()
                if (query != "") {
                    searchViewModel.search(query)
                    searchBinding.categoriesRecycler.visibility = View.GONE
                    searchBinding.searchRecyclerView.visibility = View.VISIBLE
                } else {
                    searchBinding.categoriesRecycler.visibility = View.VISIBLE
                    searchBinding.searchRecyclerView.visibility = View.GONE
                } }
    }
}