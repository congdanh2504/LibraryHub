package com.example.libraryhub.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.libraryhub.R
import com.example.libraryhub.databinding.ActivityMainBinding
import com.example.libraryhub.model.CartBook
import com.example.libraryhub.model.User
import com.example.libraryhub.viewmodel.HomeViewModel
import com.example.libraryhub.viewmodel.MainViewModel
import com.example.libraryhub.viewmodel.ProfilePaymentViewModel
import com.example.libraryhub.viewmodel.SearchViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.lang.reflect.Type

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private val profileViewModel: ProfilePaymentViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        val navController = findNavController(R.id.nav_fragment)
        mainBinding.bottomNavigatinView.setupWithNavController(navController)
        initActions()
        initObserver()
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.getNotifications()
    }

    private fun initActions() {
        mainBinding.cart.setOnClickListener {
            startActivity(Intent(this@MainActivity, CartActivity::class.java))
        }
        mainBinding.notification.setOnClickListener {
            startActivity(Intent(this@MainActivity, NotificationActivity::class.java))
        }
    }

    private fun initObserver() {
        mainViewModel.dataStoreUser.observe(this) {
            val user = Gson().fromJson(it, User::class.java)
            Picasso.get()
                .load(user.picture)
                .placeholder(R.drawable.profileplaceholder)
                .into(mainBinding.avatar)
        }
        mainViewModel.dataStoreCart.observe(this) {
            val type: Type = object : TypeToken<ArrayList<CartBook?>?>() {}.type
            val cart: ArrayList<CartBook> = Gson().fromJson(it, type) ?: arrayListOf()
            if (cart.isNotEmpty()) {
                mainBinding.cartBadge.apply {
                    visibility = View.VISIBLE
                    text = cart.size.toString()
                }
            } else {
                mainBinding.cartBadge.visibility = View.GONE
            }
        }
        mainViewModel.notification.observe(this) {
            val unSeenCount = it.count { notification -> !notification.isSeen }
            mainBinding.notificationBadge.apply {
                if (unSeenCount > 0) {
                    visibility = View.VISIBLE
                    text = unSeenCount.toString()
                } else {
                    visibility = View.GONE
                }
            }
        }
    }
}