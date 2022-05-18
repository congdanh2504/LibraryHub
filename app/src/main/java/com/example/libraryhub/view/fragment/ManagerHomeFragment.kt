package com.example.libraryhub.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.libraryhub.adapter.AdapterTab
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

        return managerHomeBinding.root
    }
}