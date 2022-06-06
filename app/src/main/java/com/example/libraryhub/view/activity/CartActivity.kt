package com.example.libraryhub.view.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryhub.R
import com.example.libraryhub.adapter.CartAdapter
import com.example.libraryhub.databinding.ActivityCartBinding
import com.example.libraryhub.model.CartBook
import com.example.libraryhub.model.User
import com.example.libraryhub.utils.AppPreferences
import com.example.libraryhub.viewmodel.CartViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.lang.reflect.Type

@AndroidEntryPoint
class CartActivity : AppCompatActivity() {
    private lateinit var cartBinding: ActivityCartBinding
    private val cartViewModel: CartViewModel by viewModels()
    private lateinit var adapter: CartAdapter
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartBinding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(cartBinding.root)
        cartViewModel.dataStoreUser.observe(this) {
            user = Gson().fromJson(it, User::class.java)
            Picasso.get()
                .load(user.picture)
                .placeholder(R.drawable.profileplaceholder)
                .into(cartBinding.avatar)
        }
        adapter = CartAdapter(onBookChange)
        cartBinding.bookList.layoutManager = LinearLayoutManager(this)
        cartBinding.bookList.adapter = adapter
        initActions()
        initObserver()
    }

    private fun initActions() {
        cartBinding.borrowButton.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
                .setTitle("Borrowing confirm")
                .setMessage("Do you want to borrow?")
                .setCancelable(false)
                .setPositiveButton(
                    "Yes"
                ) { _, _ ->
                    if (user.isExpire()) {
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
        cartViewModel.dataStoreCart.observe(this) {
            val type: Type = object : TypeToken<ArrayList<CartBook?>?>() {}.type
            val cart: ArrayList<CartBook> = Gson().fromJson(it, type)
            adapter.setBooks(cart)
        }
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

    private val onBookChange : (cart: ArrayList<CartBook>) -> Unit = {
        cartViewModel.saveCart(it)
    }

    private fun showSnackBar(msg: String) {
        Snackbar.make(
            cartBinding.avatar,
            msg,
            Snackbar.LENGTH_LONG
        ).also { snackbar ->
            snackbar.setAction("Ok") {
                snackbar.dismiss()
            }
        }.show()
    }
}