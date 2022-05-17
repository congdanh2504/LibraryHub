package com.example.libraryhub.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryhub.R
import com.example.libraryhub.adapter.AdapterCart
import com.example.libraryhub.databinding.ActivityCartBinding
import com.example.libraryhub.model.Book
import com.example.libraryhub.model.CartBook
import com.example.libraryhub.utils.AppPreferences
import com.example.libraryhub.viewmodel.CartViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : AppCompatActivity() {
    private lateinit var cartBinding: ActivityCartBinding
    private val cartViewModel: CartViewModel by viewModels()
    private lateinit var adapter: AdapterCart
    private val user = AppPreferences.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartBinding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(cartBinding.root)
        Picasso.get()
            .load(AppPreferences.user?.picture)
            .placeholder(R.drawable.profileplaceholder)
            .into(cartBinding.avatar)
        adapter = AdapterCart()
        cartBinding.bookList.layoutManager = LinearLayoutManager(this)
        cartBinding.bookList.adapter = adapter
        AppPreferences.cart?.let { adapter.setBooks(it) }
        initActions()
        initObserver()
    }

    private fun initActions() {
        cartBinding.borrowButton.setOnClickListener {
            if (user!!.isExpire()) {
                showSnackBar("You are not allow to borrow, please buy a packet!")
                return@setOnClickListener
            }
            if (user.isBorrowing) {
                showSnackBar("You are borrowing, please return before borrow this one")
                return@setOnClickListener
            }
            val selectedBooks = adapter.getSelectedBooks()
            var quantitySum = 0;
            selectedBooks.forEach {
                quantitySum += it.quantity
            }
            if (quantitySum == 0) {
                showSnackBar("Please select books to borrow")
                return@setOnClickListener
            }
            if (quantitySum > user.currentPackage!!.booksPerLoan) {
                showSnackBar("The number of books exceeds the allowable limit")
                return@setOnClickListener
            }
            cartViewModel.borrowBook(selectedBooks.toList())
        }
    }

    private fun initObserver() {
        cartViewModel.checkQuantityState.observe(this) {
            if (!it) {
                showSnackBar("Some books are out of stock")
            }
        }
        cartViewModel.borrowState.observe(this) {
            if (it) {
                showSnackBar("Borrow successfully!")
                adapter.deleteSelectedBooks()
            } else {
                showSnackBar("Borrow failed!")
            }
        }
    }

    private fun showSnackBar(msg: String) {
        Snackbar.make(
            cartBinding.textView,
            msg,
            Snackbar.LENGTH_LONG
        ).show()
    }
}