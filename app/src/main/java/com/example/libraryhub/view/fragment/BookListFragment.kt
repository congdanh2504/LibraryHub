package com.example.libraryhub.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryhub.adapter.BookAdapter
import com.example.libraryhub.databinding.FragmentBookListBinding
import com.example.libraryhub.model.Book
import com.example.libraryhub.viewmodel.BookListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookListFragment : Fragment() {
    private lateinit var bookListBinding: FragmentBookListBinding
    private val args: BookListFragmentArgs by navArgs()
    private val bookListViewModel: BookListViewModel by viewModels()
    private lateinit var adapter: BookAdapter
    private var isEnd = false
    private var isLoading = false
    private var skip = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bookListBinding = FragmentBookListBinding.inflate(inflater, container, false)
        bookListBinding.bookList.layoutManager = LinearLayoutManager(context)
        bookListBinding.categoryName.text = args.category.name
        adapter = BookAdapter(onBookClick)

        bookListBinding.bookList.adapter = adapter
        bookListViewModel.getBooksByCategory(args.category._id, skip)
        initObserver()
        initActions()
        return bookListBinding.root
    }

    private fun initActions() {
        bookListBinding.bookList.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE && !isEnd && !isLoading) {
                    bookListBinding.progressBar.visibility = View.VISIBLE
                    isLoading = true
                    bookListViewModel.getBooksByCategory(args.category._id, skip)
                }
            }
        })
    }

    private fun initObserver() {
        bookListViewModel.books.observe(viewLifecycleOwner) {
            isLoading = false
            bookListBinding.progressBar.visibility = View.GONE
            if (it.isNotEmpty()) {
                bookListBinding.bookList.visibility = View.VISIBLE
                bookListBinding.emptyImage.visibility = View.GONE
                bookListBinding.emptyText.visibility = View.GONE
                adapter.setBook(it)
                skip += it.size
            } else {
                isEnd = true
            }
        }
    }

    private val onBookClick: (book: Book) -> Unit = { book ->
        val action = BookListFragmentDirections.actionBookListFragmentToBookDetailFragment(book)
        findNavController().navigate(action)
    }


}