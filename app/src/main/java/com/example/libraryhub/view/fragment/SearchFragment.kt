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
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryhub.adapter.BookAdapter
import com.example.libraryhub.adapter.SearchAdapter
import com.example.libraryhub.databinding.FragmentSearchBinding
import com.example.libraryhub.model.Book
import com.example.libraryhub.model.Category
import com.example.libraryhub.viewmodel.SearchViewModel
import com.jakewharton.rxbinding.widget.RxSearchView
import dagger.hilt.android.AndroidEntryPoint
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var searchBinding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by activityViewModels()
    private lateinit var adapter: BookAdapter
    private var isLoading = false
    private var keyWord: String = ""

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
        searchBinding.categoriesRecycler.layoutManager = GridLayoutManager(context, 2)
        searchBinding.searchRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = BookAdapter(onBookClick)
        searchBinding.searchRecyclerView.adapter = adapter
        searchBinding.searchRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE && !isLoading) {
                    searchBinding.progressBar.visibility = View.VISIBLE
                    isLoading = true
                    searchViewModel.search(keyWord)
                }
            }
        })
    }

    private fun initObserver() {
        searchViewModel.categories.observe(viewLifecycleOwner) {
            searchBinding.categoriesRecycler.adapter = SearchAdapter(it, onCategoryClick)
        }
        searchViewModel.searchingBook.observe(viewLifecycleOwner) {
            isLoading = false
            searchBinding.progressBar.visibility = View.GONE
            if (it.isNotEmpty()) {
                adapter.setBook(it)
            }
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
            .debounce(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t ->
                val query = t.toString()
                if (query != "") {
                    resetSearchState()
                    keyWord = query
                    searchViewModel.search(query)
                    searchBinding.categoriesRecycler.visibility = View.GONE
                    searchBinding.searchRecyclerView.visibility = View.VISIBLE
                } else {
                    searchBinding.categoriesRecycler.visibility = View.VISIBLE
                    searchBinding.searchRecyclerView.visibility = View.GONE
                }
            }
    }

    private fun resetSearchState() {
        adapter.setEmpty()
        searchViewModel.refreshSearching()
    }
}