package com.example.libraryhub.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryhub.R
import com.example.libraryhub.adapter.NotificationAdapter
import com.example.libraryhub.databinding.ActivityNotificationBinding
import com.example.libraryhub.utils.AppPreferences
import com.example.libraryhub.viewmodel.NotificationViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationActivity : AppCompatActivity() {
    private lateinit var notificationBinding: ActivityNotificationBinding
    private val notificationViewModel: NotificationViewModel by viewModels()
    private lateinit var adapter: NotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificationBinding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(notificationBinding.root)
        Picasso.get()
            .load(AppPreferences.user?.picture)
            .placeholder(R.drawable.profileplaceholder)
            .into(notificationBinding.avatar)
        notificationViewModel.getNotifications()
        initRecyclerView()
        initActions()
        initObserver()
    }

    private fun initRecyclerView() {
        adapter = NotificationAdapter()
        notificationBinding.notificationRecycler.layoutManager = LinearLayoutManager(this@NotificationActivity)
        notificationBinding.notificationRecycler.adapter = adapter
    }

    private fun initActions() {
        notificationBinding.cart.setOnClickListener {
            startActivity(Intent(this@NotificationActivity, CartActivity::class.java))
            finish()
        }
        notificationBinding.swipeToRefresh.setOnRefreshListener {
            notificationViewModel.getNotifications()
            notificationBinding.swipeToRefresh.isRefreshing = false
        }
    }

    private fun initObserver() {
        notificationViewModel.notification.observe(this@NotificationActivity) {
            if (it.isNotEmpty()) {
                notificationBinding.notificationRecycler.visibility = View.VISIBLE
                notificationBinding.emptyImage.visibility = View.GONE
                notificationBinding.emptyText.visibility = View.GONE
                adapter.setNotifications(it)
            } else {
                notificationBinding.notificationRecycler.visibility = View.GONE
                notificationBinding.emptyImage.visibility = View.VISIBLE
                notificationBinding.emptyText.visibility = View.VISIBLE
            }
        }
    }
}