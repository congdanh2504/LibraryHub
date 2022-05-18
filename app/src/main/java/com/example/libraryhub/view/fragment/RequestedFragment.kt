package com.example.libraryhub.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.libraryhub.R
import com.example.libraryhub.databinding.FragmentRequestedBinding

class RequestedFragment : Fragment() {
    private lateinit var requestedBinding: FragmentRequestedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requestedBinding = FragmentRequestedBinding.inflate(inflater, container, false)

        return requestedBinding.root
    }

}