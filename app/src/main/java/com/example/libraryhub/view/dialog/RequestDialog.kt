package com.example.libraryhub.view.dialog

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDialog
import com.example.libraryhub.databinding.RequestDialogBinding
import com.example.libraryhub.model.Category
import com.example.libraryhub.model.RequestedBook

class RequestDialog(
    context: Context,
    private val categories: List<Category>,
    private val onImageClick: () -> Unit,
    private val onRequest: (RequestedBook) -> Unit
) : AppCompatDialog(context) {
    private lateinit var binding: RequestDialogBinding
    var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RequestDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter =
            ArrayAdapter<Category>(context, android.R.layout.simple_list_item_1, categories)
        binding.category.adapter = adapter
        initActions()
    }

    private fun initActions() {
        binding.picture.setOnClickListener {
            onImageClick()
        }
        binding.requestButton.setOnClickListener {
            val name = binding.name.text.toString()
            val des = binding.description.text.toString()
            val author = binding.author.text.toString()
            if (name.isEmpty()) {
                binding.name.error = "Required!"
                return@setOnClickListener
            }
            if (des.isEmpty()) {
                binding.description.error = "Required!"
                return@setOnClickListener
            }
            if (author.isEmpty()) {
                binding.author.error = "Required!"
                return@setOnClickListener
            }
            if (uri == null) {
                binding.textView3.error = "Please select a picture"
                return@setOnClickListener
            }
            val category = binding.category.selectedItem as Category
            val requestedBook = RequestedBook(name, des, author, category)
            onRequest(requestedBook)
            dismiss()
        }
    }

    fun setPicture() {
        binding.picture.setImageURI(uri)
    }

}