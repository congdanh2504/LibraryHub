package com.example.libraryhub.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.libraryhub.databinding.FragmentBorrowerRecordBinding

class BorrowerRecordFragment : Fragment() {
    private lateinit var borrowerRecordBinding: FragmentBorrowerRecordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        borrowerRecordBinding = FragmentBorrowerRecordBinding.inflate(inflater, container, false)

        return borrowerRecordBinding.root
    }
}