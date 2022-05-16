package com.example.libraryhub.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryhub.R
import com.example.libraryhub.adapter.AdapterReview
import com.example.libraryhub.databinding.FragmentBookDetailBinding
import com.example.libraryhub.model.Book
import com.example.libraryhub.model.Review
import com.example.libraryhub.utils.AppPreferences
import com.example.libraryhub.viewmodel.BookDetailViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDetailFragment : Fragment() {
    private lateinit var bookDetailBinding: FragmentBookDetailBinding
    private val args: BookDetailFragmentArgs by navArgs()
    private lateinit var reviewAdapter: AdapterReview
    private val bookDetailViewModel: BookDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bookDetailBinding = FragmentBookDetailBinding.inflate(inflater, container, false)
        initBook(args.book)
        initRecyclerView()
        initActions()
        return bookDetailBinding.root
    }

    private fun initBook(book: Book) {
        bookDetailBinding.name.text = book.name
        bookDetailBinding.name.text = book.name
        bookDetailBinding.description.text = book.description
        bookDetailBinding.author.text = book.author
        bookDetailBinding.ratingBar.rating = book.avgRate.toFloat()
        bookDetailBinding.category.text = book.category.name
        bookDetailBinding.location.text = book.location.toString()
        bookDetailBinding.quantity.text = book.quantity.toString()
        bookDetailBinding.publishYear.text = book.publishYear.toString()
        bookDetailBinding.price.text = "${book.price} VND"
        Picasso.get()
            .load(book.picture)
            .fit()
            .centerCrop()
            .placeholder(R.drawable.placeholdeimage)
            .into(bookDetailBinding.image)
    }

    private fun initRecyclerView() {
        reviewAdapter = AdapterReview()
        bookDetailBinding.commentRecyclerView.layoutManager = LinearLayoutManager(context)
        reviewAdapter.setReviews(args.book.reviews)
        bookDetailBinding.commentRecyclerView.adapter = reviewAdapter

        bookDetailViewModel.book.observe(viewLifecycleOwner) {
            initBook(it)
            reviewAdapter.setReviews(it.reviews)
        }
    }

    private fun initActions() {
        bookDetailBinding.commentButton.setOnClickListener {
            val comment: String = bookDetailBinding.comment.text.toString()
            if (comment != "") {
                val rating: Double = bookDetailBinding.ratingBar2.rating.toDouble()
                val review = Review(comment, rating, AppPreferences.user!!)
                bookDetailViewModel.addReview(args.book._id, review)
            } else {
                Toast.makeText(activity, "Comment must be not null", Toast.LENGTH_LONG).show()
            }
        }
    }
}