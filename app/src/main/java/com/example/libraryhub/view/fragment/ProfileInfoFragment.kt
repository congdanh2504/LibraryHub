package com.example.libraryhub.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.libraryhub.databinding.FragmentProfileInfoBinding
import com.example.libraryhub.utils.AppPreferences
import com.example.libraryhub.view.activity.LoginActivity
import com.example.libraryhub.viewmodel.ProfileInfoViewModel
import com.onesignal.OneSignal
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ProfileInfoFragment : Fragment() {
    private lateinit var fragmentProfileInfoBinding: FragmentProfileInfoBinding
    private val user = AppPreferences.user
    private val profileInfoViewModel: ProfileInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentProfileInfoBinding = FragmentProfileInfoBinding.inflate(inflater,container,false)

        fragmentProfileInfoBinding.email.text = user!!.email
        fragmentProfileInfoBinding.username.text = user.username

        if (!user.isExpire()) {
            fragmentProfileInfoBinding.currentPackage.text = user.currentPackage!!.name
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            fragmentProfileInfoBinding.expiration.text = dateFormatter.format(user.expiration!!)
        }
        if (user.isBorrowing) {
            fragmentProfileInfoBinding.state.text = "Is borrowing"
        } else {
            fragmentProfileInfoBinding.state.text = "Isn't borrowing"
        }
        initActions()
        return fragmentProfileInfoBinding.root
    }

    private fun initActions() {
        fragmentProfileInfoBinding.signOutButton.setOnClickListener {
            if (OneSignal.getDeviceState() != null) {
                profileInfoViewModel.deleteDeviceId(OneSignal.getDeviceState()!!.userId)
                profileInfoViewModel.deleteState.observe(viewLifecycleOwner) {
                    AppPreferences.JWT = ""
                    AppPreferences.user = null
                    AppPreferences.cart = null
                    startActivity(Intent(context, LoginActivity::class.java))
                    activity?.finish()
                }
            } else {
                AppPreferences.JWT = ""
                AppPreferences.user = null
                AppPreferences.cart = null
                startActivity(Intent(context, LoginActivity::class.java))
                activity?.finish()
            }
        }
    }
}