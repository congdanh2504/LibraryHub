package com.example.libraryhub.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryhub.adapter.PackageAdapter
import com.example.libraryhub.databinding.FragmentProfilePaymentBinding
import com.example.libraryhub.model.User
import com.example.libraryhub.utils.AppPreferences
import com.example.libraryhub.viewmodel.ProfilePaymentViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfilePaymentFragment : Fragment() {
    private lateinit var paymentBinding: FragmentProfilePaymentBinding
    private val profileViewModel: ProfilePaymentViewModel by activityViewModels()
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        paymentBinding = FragmentProfilePaymentBinding.inflate(inflater, container, false)
        paymentBinding.subscriptionRecyclerView.layoutManager = LinearLayoutManager(context)
        profileViewModel.packages.observe(viewLifecycleOwner) {
            paymentBinding.subscriptionRecyclerView.adapter = PackageAdapter(it, onBuy, requireContext())
        }
        profileViewModel.dataStoreUser.observe(viewLifecycleOwner) {
            val gson = Gson()
            user = gson.fromJson(it, User::class.java)
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
                showSnackBar("Buying successfully")
            } else {
               showSnackBar("Buying failed: You are using ${user.currentPackage!!.name} package")
            }
        }
    }

    private fun showSnackBar(msg: String) {
        Snackbar.make(
            paymentBinding.subscriptionRecyclerView,
            msg,
            Snackbar.LENGTH_LONG
        ).show()
    }
}