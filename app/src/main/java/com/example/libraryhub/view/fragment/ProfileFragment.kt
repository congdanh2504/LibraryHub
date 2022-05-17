package com.example.libraryhub.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.libraryhub.adapter.AdapterTab
import com.example.libraryhub.R
import com.example.libraryhub.databinding.FragmentProfileBinding
import com.example.libraryhub.utils.AppPreferences
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
        val data = ArrayList<Fragment>()
        data.add(ProfileInfoFragment())
        data.add(ProfilePaymentFragment())
        val adapter = AdapterTab(this, data)
        viewPager = profileBinding.viewPager
        viewPager.adapter = adapter
        val tabLayout = profileBinding.profileTabLayout
        TabLayoutMediator(tabLayout,viewPager){tab,position ->
            when(position){
                0 ->{
                    tab.text = "Infomation"
                }
                1 ->{
                    tab.text = "Payment"
                }
            }
        }.attach()


        return profileBinding.root
    }

    private fun initProfile() {
        Picasso.get()
            .load(user?.picture)
            .placeholder(R.drawable.profileplaceholder)
            .into(profileBinding.avatar)
        profileBinding.username.text = user?.username
    }


}