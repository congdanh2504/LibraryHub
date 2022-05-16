package com.example.libraryhub.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryhub.adapter.AdapterPackage
import com.example.libraryhub.adapter.AdapterSubscription
import com.example.libraryhub.databinding.FragmentProfilePaymentBinding
import com.example.libraryhub.model.Package
import com.example.libraryhub.model.Subscription
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfilePaymentFragment : Fragment() {
    private lateinit var paymentBinding: FragmentProfilePaymentBinding
    private val profileViewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        paymentBinding = FragmentProfilePaymentBinding.inflate(inflater, container, false)
        paymentBinding.subscriptionRecyclerView.layoutManager = LinearLayoutManager(context)
        profileViewModel.packages.observe(viewLifecycleOwner) {
            paymentBinding.subscriptionRecyclerView.adapter = AdapterPackage(it)
        }

        return paymentBinding.root
    }
}