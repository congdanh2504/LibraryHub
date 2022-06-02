package com.example.libraryhub.view.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryhub.R
import com.example.libraryhub.adapter.BorrowerAdapter
import com.example.libraryhub.databinding.ActivityManagerRecordBinding
import com.example.libraryhub.model.BorrowerRecord
import com.example.libraryhub.model.User
import com.example.libraryhub.utils.AppPreferences
import com.example.libraryhub.viewmodel.AdminViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ManagerRecordActivity : AppCompatActivity() {
    private lateinit var managerRecordBinding: ActivityManagerRecordBinding
    private val adminViewModel: AdminViewModel by viewModels()
    private lateinit var adapter: BorrowerAdapter
    private lateinit var record: BorrowerRecord

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        managerRecordBinding = ActivityManagerRecordBinding.inflate(layoutInflater)
        setContentView(managerRecordBinding.root)
        record = intent.getParcelableExtra<BorrowerRecord>("record") as BorrowerRecord
        initView()
        initActions()
        initObserver()
    }

    private fun initView() {
        adminViewModel.dataStoreUser.observe(this) {
            val gson = Gson()
            val user = gson.fromJson(it, User::class.java)
            Picasso.get()
                .load(user?.picture)
                .placeholder(R.drawable.profileplaceholder)
                .into(managerRecordBinding.avatar)
        }
        Picasso.get()
            .load(record.user.picture)
            .placeholder(R.drawable.profileplaceholder)
            .into(managerRecordBinding.userAvatar)
        managerRecordBinding.borrowingRecycler.layoutManager = LinearLayoutManager(this)
        adapter = BorrowerAdapter()
        managerRecordBinding.borrowingRecycler.adapter = adapter
        adapter.setBooks(record.books)
        managerRecordBinding.username.text = record.user.username
        managerRecordBinding.status.text = record.status
        val dateFormatter = SimpleDateFormat("EEE, MMM d, yyyy HH:mm", Locale.getDefault())
        managerRecordBinding.createdDate.text = dateFormatter.format(record.createdDate)
        managerRecordBinding.returnDate.text = dateFormatter.format(record.returnDate)
        if (!record.status.startsWith("Pending")) managerRecordBinding.acceptButton.visibility = View.GONE
    }

    private fun initActions() {
        managerRecordBinding.acceptButton.setOnClickListener {
            adminViewModel.confirm(record._id)
        }
    }

    private fun initObserver() {
        adminViewModel.confirmState.observe(this) {
            if (it) {
                val snackBar = showSnackBar("Successfully!")
                snackBar.show()
                snackBar.addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        super.onDismissed(transientBottomBar, event)
                        finish()
                    }
                })
            } else {
                showSnackBar("Failed!").show()
            }
        }
    }

    private fun showSnackBar(msg: String): Snackbar {
        return Snackbar.make(
            managerRecordBinding.notification,
            msg,
            Snackbar.LENGTH_SHORT
        )
    }
}