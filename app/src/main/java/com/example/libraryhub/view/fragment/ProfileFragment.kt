package com.example.libraryhub.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.libraryhub.adapter.TabAdapter
import com.example.libraryhub.R
import com.example.libraryhub.databinding.FragmentProfileBinding
import com.example.libraryhub.model.User
import com.example.libraryhub.utils.AppPreferences
import com.example.libraryhub.viewmodel.ProfileViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var profileBinding: FragmentProfileBinding
    private lateinit var viewPager: ViewPager2
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileBinding = FragmentProfileBinding.inflate(inflater,container,false)
        initViewPager()
        initProfile()

        return profileBinding.root
    }

    private fun initViewPager() {
        val data = ArrayList<Fragment>()
        data.add(ProfileInfoFragment())
        data.add(ProfilePaymentFragment())
        val adapter = TabAdapter(this, data)
        viewPager = profileBinding.viewPager
        viewPager.adapter = adapter
        val tabLayout = profileBinding.profileTabLayout
        TabLayoutMediator(tabLayout,viewPager){tab,position ->
            when(position){
                0 ->{
                    tab.text = "Information"
                }
                1 ->{
                    tab.text = "Payment"
                }
            }
        }.attach()
    }

    private fun initProfile() {
        profileViewModel.dataStoreUser.observe(viewLifecycleOwner) {
            val gson = Gson()
            val user = gson.fromJson(it, User::class.java)
            Picasso.get()
                .load(user.picture)
                .placeholder(R.drawable.profileplaceholder)
                .into(profileBinding.avatar)
            profileBinding.username.text = user?.username
        }
    }
}