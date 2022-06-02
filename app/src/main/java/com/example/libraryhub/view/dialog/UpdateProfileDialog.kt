package com.example.libraryhub.view.dialog

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialog
import com.example.libraryhub.databinding.UpdateProfileDialogBinding
import com.example.libraryhub.model.User
import com.example.libraryhub.utils.AppPreferences

class UpdateProfileDialog(
    context: Context,
    private val onUpdateProfile: (String) -> Unit,
    private val onImageClick: () -> Unit
) :
    AppCompatDialog(context) {
    private lateinit var binding: UpdateProfileDialogBinding
    var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UpdateProfileDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.avatar.setOnClickListener {
            onImageClick()
        }
        binding.updateButton.setOnClickListener {
            val username = binding.username.text.toString()
            if (username.isBlank()) {
                binding.username.error = "Required!"
                return@setOnClickListener
            }
            if (uri == null) {
                binding.textView3.error = "Please choose an image"
            }
            onUpdateProfile(username)
            dismiss()
        }
    }

    fun setPicture() {
        binding.avatar.setImageURI(uri)
    }
}