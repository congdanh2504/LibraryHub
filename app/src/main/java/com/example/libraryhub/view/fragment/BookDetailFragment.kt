package com.example.libraryhub.view.fragment

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryhub.R
import com.example.libraryhub.adapter.ReviewAdapter
import com.example.libraryhub.databinding.FragmentBookDetailBinding
import com.example.libraryhub.model.Book
import com.example.libraryhub.model.CartBook
import com.example.libraryhub.model.Review
import com.example.libraryhub.model.User
import com.example.libraryhub.utils.AppPreferences
import com.example.libraryhub.viewmodel.BookDetailViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.lang.reflect.Type

@AndroidEntryPoint
class BookDetailFragment : Fragment() {
    private lateinit var bookDetailBinding: FragmentBookDetailBinding
    private val args: BookDetailFragmentArgs by navArgs()
    private lateinit var reviewAdapter: ReviewAdapter
    private val bookDetailViewModel: BookDetailViewModel by viewModels()
    private lateinit var user: User
    private lateinit var cart: ArrayList<CartBook>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bookDetailBinding = FragmentBookDetailBinding.inflate(inflater, container, false)
        bookDetailViewModel.dataStoreUser.observe(viewLifecycleOwner) {
            val gson = Gson()
            user = gson.fromJson(it, User::class.java)
        }
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
        reviewAdapter = ReviewAdapter()
        bookDetailBinding.commentRecyclerView.layoutManager = LinearLayoutManager(context)
        reviewAdapter.setReviews(args.book.reviews)
        bookDetailBinding.commentRecyclerView.adapter = reviewAdapter

        bookDetailViewModel.book.observe(viewLifecycleOwner) {
            initBook(it)
            reviewAdapter.setReviews(it.reviews)
        }
    }

    private fun initActions() {
        bookDetailViewModel.dataStoreCart.observe(viewLifecycleOwner) {
            val type: Type = object : TypeToken<ArrayList<CartBook?>?>() {}.type
            cart = Gson().fromJson(it, type) ?: arrayListOf()
        }

        bookDetailBinding.commentButton.setOnClickListener {
            val comment: String = bookDetailBinding.comment.text.toString()
            if (comment != "") {
                val rating: Double = bookDetailBinding.ratingBar2.rating.toDouble()
                val review = Review(comment, rating, user)
                bookDetailViewModel.addReview(args.book._id, review)
            } else {
                Snackbar.make(
                    bookDetailBinding.textView10,
                    "Comment must be not null",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            bookDetailBinding.comment.text = SpannableStringBuilder("")
        }
        bookDetailBinding.addToCart.setOnClickListener {
            var isContain = false
            for (i in 0 until cart.size) {
                if (cart[i].id == args.book._id) {
                    cart[i].quantity++
                    isContain = true
                    break
                }
            }
            if (!isContain) {
                cart.add(
                    CartBook(
                        args.book._id,
                        args.book.name,
                        args.book.picture,
                        args.book.author,
                        1,
                        false
                    )
                )
            }
            bookDetailViewModel.saveCart(cart)
            Snackbar.make(
                bookDetailBinding.textView10,
                """Add "${args.book.name}" to the cart""",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
}