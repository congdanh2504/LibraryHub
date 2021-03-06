package com.example.libraryhub.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.libraryhub.R

import com.example.libraryhub.databinding.ActivityManagerMainBinding
import com.example.libraryhub.model.User
import com.example.libraryhub.utils.AppPreferences
import com.example.libraryhub.viewmodel.AdminViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagerMainActivity : AppCompatActivity() {
    private lateinit var managerMainBinding: ActivityManagerMainBinding
    private val adminViewModel: AdminViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        managerMainBinding = ActivityManagerMainBinding.inflate(layoutInflater)
        setContentView(managerMainBinding.root)
        val navController = findNavController(R.id.nav_fragment)
        managerMainBinding.bottomNavigatinView.setupWithNavController(navController)
        adminViewModel.dataStoreUser.observe(this) {
            val gson = Gson()
            val user = gson.fromJson(it, User::class.java)
            Picasso.get()
                .load(user?.picture)
                .placeholder(R.drawable.profileplaceholder)
                .into(managerMainBinding.avatar)
        }
        initActions()
        initObserver()
    }

    override fun onResume() {
        super.onResume()
        adminViewModel.getNotifications()
    }

    private fun initActions() {
        managerMainBinding.fab.setOnClickListener {
            val intentIntegrator = IntentIntegrator(this)
            intentIntegrator.setDesiredBarcodeFormats(listOf(IntentIntegrator.QR_CODE))
            intentIntegrator.initiateScan()
        }
        managerMainBinding.notification.setOnClickListener {
            startActivity(Intent(this@ManagerMainActivity, NotificationActivity::class.java))
        }
    }

    private fun initObserver() {
        adminViewModel.getRecordState.observe(this) {
            if (it) {
                Intent(this@ManagerMainActivity, ManagerRecordActivity::class.java).also { intent ->
                    intent.putExtra("record", adminViewModel.record.value!!)
                    startActivity(intent)
                }
            } else {
                showSnackBar("This QR Code is invalid")
            }
        }
        adminViewModel.notification.observe(this) {
            val unSeenCount = it.count { notification ->  !notification.isSeen }
            managerMainBinding.notificationBadge.apply {
                if (unSeenCount > 0) {
                    visibility = View.VISIBLE
                    text = unSeenCount.toString()
                } else {
                    visibility = View.GONE
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(resultCode, data)
        result.contents?.let {
            adminViewModel.getRecordById(it)
        }
    }

    private fun showSnackBar(msg: String) {
        Snackbar.make(
            managerMainBinding.notification,
            msg,
            Snackbar.LENGTH_LONG
        ).show()
    }
}