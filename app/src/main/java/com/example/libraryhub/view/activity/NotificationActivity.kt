package com.example.libraryhub.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.libraryhub.R
import com.example.libraryhub.databinding.ActivityNotificationBinding
import com.example.libraryhub.utils.AppPreferences
import com.squareup.picasso.Picasso

class NotificationActivity : AppCompatActivity() {
    private lateinit var notificationBinding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificationBinding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(notificationBinding.root)
        Picasso.get()
            .load(AppPreferences.user?.picture)
            .placeholder(R.drawable.profileplaceholder)
            .into(notificationBinding.avatar)
        initActions()
    }

    private fun initActions() {
        notificationBinding.cart.setOnClickListener {
            startActivity(Intent(this@NotificationActivity, CartActivity::class.java))
            finish()
        }
    }
}