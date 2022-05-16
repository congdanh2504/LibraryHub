package com.example.libraryhub.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryhub.R
import com.example.libraryhub.adapter.AdapterCart
import com.example.libraryhub.databinding.ActivityCartBinding
import com.example.libraryhub.model.Book
import com.example.libraryhub.model.CartBook
import com.example.libraryhub.utils.AppPreferences
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : AppCompatActivity() {
    private lateinit var cartBinding: ActivityCartBinding
    private lateinit var adapter: AdapterCart

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
    }

}