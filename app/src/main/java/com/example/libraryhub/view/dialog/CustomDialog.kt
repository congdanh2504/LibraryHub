package com.example.libraryhub.view.dialog

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialog
import com.example.libraryhub.databinding.QrcodeDialogBinding

class CustomDialog(context: Context,private val bitMatrix: Bitmap) : AppCompatDialog(context) {
    private lateinit var binding: QrcodeDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = QrcodeDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.qrImage.setImageBitmap(bitMatrix)
    }
}