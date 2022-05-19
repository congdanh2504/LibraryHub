package com.example.libraryhub.view.activity

import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryhub.R
import com.example.libraryhub.adapter.CartAdapter
import com.example.libraryhub.databinding.ActivityCartBinding
import com.example.libraryhub.utils.AppPreferences
import com.example.libraryhub.viewmodel.CartViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : AppCompatActivity() {
    private lateinit var cartBinding: ActivityCartBinding
    private val cartViewModel: CartViewModel by viewModels()
    private lateinit var adapter: CartAdapter
    private val user = AppPreferences.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartBinding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(cartBinding.root)
        Picasso.get()
            .load(AppPreferences.user?.picture)
            .placeholder(R.drawable.profileplaceholder)
            .into(cartBinding.avatar)
        adapter = CartAdapter()
        cartBinding.bookList.layoutManager = LinearLayoutManager(this)
        cartBinding.bookList.adapter = adapter
        AppPreferences.cart?.let { adapter.setBooks(it) }
        initActions()
        initObserver()
    }

    private fun initActions() {
        cartBinding.borrowButton.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
                .setTitle("Borrowing confirm")
                .setMessage("Do you want to return?")
                .setCancelable(false)
                .setPositiveButton(
                    "Yes"
                ) { _, _ ->
                    if (user!!.isExpire()) {
                        showSnackBar("Error: You are not allow to borrow, please buy a packet!")
                        return@setPositiveButton
                    }
                    if (user.isBorrowing) {
                        showSnackBar("Error: You are borrowing, please return before borrow this one")
                        return@setPositiveButton
                    }
                    val selectedBooks = adapter.getSelectedBooks()
                    var quantitySum = 0;
                    selectedBooks.forEach {
                        quantitySum += it.quantity
                    }
                    if (quantitySum == 0) {
                        showSnackBar("Error: Please select books to borrow")
                        return@setPositiveButton
                    }
                    if (quantitySum > user.currentPackage!!.booksPerLoan) {
                        showSnackBar("Error: The number of books exceeds the allowable limit")
                        return@setPositiveButton
                    }
                    cartViewModel.borrowBook(selectedBooks.toList())
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.cancel()
                }
                .create()
            alertDialog.show()
        }
    }

    private fun initObserver() {
        cartViewModel.checkQuantityState.observe(this) {
            if (!it) {
                showSnackBar("Error: Some books are out of stock")
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
        ).also { snackbar ->
            snackbar.setAction("Ok") {
                snackbar.dismiss()
            }
        }.show()
    }
}