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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bookListBinding = FragmentBookListBinding.inflate(inflater, container, false)
        bookListBinding.bookList.layoutManager = LinearLayoutManager(context)
        bookListBinding.categoryName.text = args.category.name
        adapter = BookAdapter(onBookClick)

        bookListBinding.bookList.adapter = adapter

        bookListViewModel.books.observe(viewLifecycleOwner) {
            bookListBinding.bookList.visibility = View.VISIBLE
            bookListBinding.emptyImage.visibility = View.GONE
            bookListBinding.emptyText.visibility = View.GONE
            adapter.setBook(it)
        }
        bookListViewModel.getBooksByCategory(args.category._id)

        return bookListBinding.root
    }

    private val onBookClick: (book: Book) -> Unit = { book ->
        val action = BookListFragmentDirections.actionBookListFragmentToBookDetailFragment(book)
        findNavController().navigate(action)
    }

}