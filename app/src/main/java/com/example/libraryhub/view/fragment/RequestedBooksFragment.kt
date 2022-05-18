package com.example.libraryhub.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.libraryhub.databinding.FragmentRequestedBooksBinding

class RequestedBooksFragment : Fragment() {
    private lateinit var requestedBooksBinding: FragmentRequestedBooksBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requestedBooksBinding = FragmentRequestedBooksBinding.inflate(inflater, container, false)

        return requestedBooksBinding.root
    }
}