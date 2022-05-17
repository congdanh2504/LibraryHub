package com.example.libraryhub.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.libraryhub.adapter.AdapterTab
import com.example.libraryhub.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {
    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)

        val data = ArrayList<Fragment>()
        data.add(BorrowingFragment())
        data.add(RecentFragment())
        data.add(RequestedFragment())
        val adapter = AdapterTab(this, data)
        viewPager = homeBinding.homeViewPager
        viewPager.adapter = adapter
        val tabLayout = homeBinding.HomeTabLayout
        TabLayoutMediator(tabLayout,viewPager){tab,position ->
            when(position){
                0 ->{
                    tab.text = "Borrowing"
                }
                1 ->{
                    tab.text = "Recent"
                }
                2 ->{
                    tab.text = "Requested"
                }
            }
        }.attach()

        return homeBinding.root
    }
}