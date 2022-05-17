package com.example.libraryhub.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.libraryhub.R
import com.example.libraryhub.databinding.ActivityMainBinding
import com.example.libraryhub.utils.AppPreferences
import com.example.libraryhub.viewmodel.HomeViewModel
import com.example.libraryhub.viewmodel.ProfileViewModel
import com.example.libraryhub.viewmodel.SearchViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private val profileViewModel: ProfileViewModel by viewModels()
    private val searchViewModel: SearchViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        val navController = findNavController(R.id.nav_fragment)
        mainBinding.bottomNavigatinView.setupWithNavController(navController)
        Picasso.get()
            .load(AppPreferences.user?.picture)
            .placeholder(R.drawable.profileplaceholder)
            .into(mainBinding.avatar)
        initActions()
    }

    private fun initActions() {
        mainBinding.cart.setOnClickListener {
            startActivity(Intent(this@MainActivity, CartActivity::class.java))
        }
    }
}