package com.example.libraryhub.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.libraryhub.R
import com.example.libraryhub.databinding.FragmentManagerProfileBinding
import com.example.libraryhub.model.User
import com.example.libraryhub.utils.AppPreferences
import com.example.libraryhub.view.activity.LoginActivity
import com.example.libraryhub.viewmodel.ProfileInfoViewModel
import com.google.gson.Gson
import com.onesignal.OneSignal
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagerProfileFragment : Fragment() {
    private lateinit var managerProfileBinding: FragmentManagerProfileBinding
    private lateinit var user: User
    private val profileInfoViewModel: ProfileInfoViewModel by viewModels()

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
        profileInfoViewModel.dataStoreUser.observe(viewLifecycleOwner) {
            val gson = Gson()
            user = gson.fromJson(it, User::class.java)
            Picasso.get()
                .load(user.picture)
                .placeholder(R.drawable.profileplaceholder)
                .into(managerProfileBinding.avatar)
            managerProfileBinding.cUsername.text = user.username
            managerProfileBinding.username.text = user.username
            managerProfileBinding.email.text = user.email
        }

    }

    private fun initActions() {
        managerProfileBinding.signOutButton.setOnClickListener {
            if (OneSignal.getDeviceState() != null) {
                profileInfoViewModel.deleteDeviceId(OneSignal.getDeviceState()!!.userId)
                profileInfoViewModel.deleteState.observe(viewLifecycleOwner) {
                    AppPreferences.JWT = ""
                    startActivity(Intent(context, LoginActivity::class.java))
                    activity?.finish()
                }
            } else {
                AppPreferences.JWT = ""
                startActivity(Intent(context, LoginActivity::class.java))
                activity?.finish()
            }
        }
    }

}