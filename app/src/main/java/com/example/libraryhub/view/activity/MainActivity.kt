package com.example.libraryhub.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.libraryhub.R
import com.example.libraryhub.databinding.ActivityMainBinding
import com.example.libraryhub.model.User
import com.example.libraryhub.viewmodel.HomeViewModel
import com.example.libraryhub.viewmodel.MainViewModel
import com.example.libraryhub.viewmodel.ProfilePaymentViewModel
import com.example.libraryhub.viewmodel.SearchViewModel
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

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

        mainViewModel.dataStoreUser.observe(this) {
            val gson = Gson()
            val user = gson.fromJson(it, User::class.java)
            Picasso.get()
                .load(user.picture)
                .placeholder(R.drawable.profileplaceholder)
                .into(mainBinding.avatar)
        }
    }

    private fun initActions() {
        mainBinding.cart.setOnClickListener {
            startActivity(Intent(this@MainActivity, CartActivity::class.java))
        }
        mainBinding.notification.setOnClickListener {
            startActivity(Intent(this@MainActivity, NotificationActivity::class.java))
        }
    }
}