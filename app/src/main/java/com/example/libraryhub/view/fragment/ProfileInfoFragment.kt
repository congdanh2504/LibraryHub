package com.example.libraryhub.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.libraryhub.R
import com.example.libraryhub.databinding.FragmentProfileBinding
import com.example.libraryhub.databinding.FragmentProfileInfoBinding
import com.example.libraryhub.utils.AppPreferences
import com.example.libraryhub.view.activity.LoginActivity

class ProfileInfoFragment : Fragment() {
    private lateinit var fragmentProfileInfoBinding: FragmentProfileInfoBinding
    private val user = AppPreferences.user

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentProfileInfoBinding = FragmentProfileInfoBinding.inflate(inflater,container,false)

        fragmentProfileInfoBinding.email.text = user!!.email
        fragmentProfileInfoBinding.username.text = user.username
        fragmentProfileInfoBinding.borrowingNum.text = user.borrowingNum.toString()
        initActions()
        return fragmentProfileInfoBinding.root
    }

    private fun initActions() {
        fragmentProfileInfoBinding.signOutButton.setOnClickListener {
            AppPreferences.JWT = ""
            AppPreferences.user = null
            startActivity(Intent(context, LoginActivity::class.java))
            activity?.finish()
        }
    }
}