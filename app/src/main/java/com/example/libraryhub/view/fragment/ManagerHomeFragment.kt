package com.example.libraryhub.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.libraryhub.adapter.TabAdapter
import com.example.libraryhub.databinding.FragmentManagerHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class ManagerHomeFragment : Fragment() {
    private lateinit var managerHomeBinding: FragmentManagerHomeBinding
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        managerHomeBinding = FragmentManagerHomeBinding.inflate(inflater, container, false)
        val data = ArrayList<Fragment>()
        data.add(BorrowerRecordFragment())
        data.add(RequestedBooksFragment())
        val adapter = TabAdapter(this, data)
        viewPager = managerHomeBinding.viewPager
        viewPager.adapter = adapter
        val tabLayout = managerHomeBinding.tabLayout
        TabLayoutMediator(tabLayout,viewPager){tab,position ->
            when(position){
                0 ->{
                    tab.text = "Borrower record"
                }
                1 ->{
                    tab.text = "Requested books"
                }
            }
        }.attach()

        return managerHomeBinding.root
    }
}