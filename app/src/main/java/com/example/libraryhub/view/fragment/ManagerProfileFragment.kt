package com.example.libraryhub.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.libraryhub.R
import com.example.libraryhub.databinding.FragmentManagerProfileBinding
import com.example.libraryhub.utils.AppPreferences
import com.example.libraryhub.view.activity.LoginActivity
import com.squareup.picasso.Picasso

class ManagerProfileFragment : Fragment() {
    private lateinit var managerProfileBinding: FragmentManagerProfileBinding
    private val user = AppPreferences.user

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        managerProfileBinding = FragmentManagerProfileBinding.inflate(inflater, container, false)
        initProfile()
        initActions()

        return managerProfileBinding.root
    }

    private fun initProfile() {
        Picasso.get()
            .load(user?.picture)
            .placeholder(R.drawable.profileplaceholder)
            .into(managerProfileBinding.avatar)
        managerProfileBinding.cUsername.text = user?.username
        managerProfileBinding.username.text = user?.username
        managerProfileBinding.email.text = user?.email
    }

    private fun initActions() {
        managerProfileBinding.signOutButton.setOnClickListener {
            AppPreferences.JWT = ""
            AppPreferences.user = null
            startActivity(Intent(context, LoginActivity::class.java))
            activity?.finish()
        }
    }

}