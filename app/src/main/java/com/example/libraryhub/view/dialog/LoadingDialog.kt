package com.example.libraryhub.view.dialog

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialog
import com.example.libraryhub.databinding.LoadingDialogBinding

class LoadingDialog(context: Context) : AppCompatDialog(context) {
    private lateinit var binding: LoadingDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoadingDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCancelable(false)
    }
}