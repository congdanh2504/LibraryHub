package com.example.libraryhub.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.example.libraryhub.R
import com.example.libraryhub.databinding.FragmentProfileBinding
import com.example.libraryhub.utils.AppPreferences
import com.example.libraryhub.view.activity.LoginActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {
    private lateinit var profileBinding: FragmentProfileBinding
    private lateinit var viewPager: ViewPager2
    private val user = AppPreferences.user
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileBinding = FragmentProfileBinding.inflate(inflater,container,false)
        initProfile()
        initActions()
//        val data = ArrayList<Fragment>()
//        data.add(ProfileInfoFragment())
//        data.add(ProfilePaymentFragment())
//        val adapter = ProfilePagerAdapter(this,data)
//        viewPager = profileBinding.viewPager
//        viewPager.adapter = adapter
//        val tabLayout = profileBinding.profileTabLayout
//        TabLayoutMediator(tabLayout,viewPager){tab,position ->
//            when(position){
//                0 ->{
//                    tab.text = "Infomation"
//                }
//                1 ->{
//                    tab.text = "Payment"
//                }
//            }
//        }.attach()


        return profileBinding.root
    }

    private fun initProfile() {
        Picasso.get()
            .load(user?.picture)
            .placeholder(R.drawable.ic_launcher_background)
            .into(profileBinding.avatar)
        profileBinding.username.text = user?.username
    }

    private fun initActions() {
        profileBinding.signOutButton.setOnClickListener {
            AppPreferences.JWT = ""
            AppPreferences.user = null
            startActivity(Intent(context, LoginActivity::class.java))
            activity?.finish()
        }
    }
}