package com.example.libraryhub.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryhub.adapter.AdapterPackage
import com.example.libraryhub.databinding.FragmentProfilePaymentBinding
import com.example.libraryhub.utils.AppPreferences
import com.example.libraryhub.viewmodel.ProfileViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ProfilePaymentFragment : Fragment() {
    private lateinit var paymentBinding: FragmentProfilePaymentBinding
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private val user = AppPreferences.user

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        paymentBinding = FragmentProfilePaymentBinding.inflate(inflater, container, false)
        paymentBinding.subscriptionRecyclerView.layoutManager = LinearLayoutManager(context)
        profileViewModel.packages.observe(viewLifecycleOwner) {
            paymentBinding.subscriptionRecyclerView.adapter = AdapterPackage(it, onBuy)
        }


        return paymentBinding.root
    }

    private val onBuy: (packageId: String) -> Unit = {
        if (!user!!.isExpire()) {
            profileViewModel._buyState.postValue(false)
        } else {
            profileViewModel.buyPackage(it)
        }
        profileViewModel.buyState.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                Snackbar.make(
                    paymentBinding.subscriptionRecyclerView,
                    "Buying successfully",
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                Snackbar.make(
                    paymentBinding.subscriptionRecyclerView,
                    "You are using ${user.currentPackage!!.name} package",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }
}