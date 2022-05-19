package com.example.libraryhub.view.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.libraryhub.databinding.FragmentRequestedBinding
import com.example.libraryhub.model.FileRequestBody
import com.example.libraryhub.model.RequestedBook
import com.example.libraryhub.view.dialog.LoadingDialog
import com.example.libraryhub.view.dialog.RequestDialog
import com.example.libraryhub.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MultipartBody

@AndroidEntryPoint
class RequestedFragment : Fragment() {
    private lateinit var requestedBinding: FragmentRequestedBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var dialog: RequestDialog
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var requestedBook: RequestedBook

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requestedBinding = FragmentRequestedBinding.inflate(inflater, container, false)
        initObserver()
        return requestedBinding.root
    }

    private fun initActions() {
        requestedBinding.fabAdd.setOnClickListener {
            dialog = RequestDialog(
                requireContext(),
                homeViewModel.categories.value!!,
                onImageClick,
                onRequest
            )
            dialog.show()
        }
    }

    @SuppressLint("Range")
    private fun getContentType(uri: Uri): String {
        var type: String? = null
        val cursor = requireActivity().contentResolver.query(uri, null, null, null, null)
        try {
            if (cursor != null) {
                cursor.moveToFirst()
                type = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE))
                cursor.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return type!!
    }

    private val onImageClick: () -> Unit = {
        openImageChooser()
    }

    private fun initObserver() {
        homeViewModel.categories.observe(viewLifecycleOwner) {
            initActions()
        }
        homeViewModel.requestState.observe(viewLifecycleOwner) {
            loadingDialog.dismiss()
            if (it) {
                showSnackBar("Request successfully!")
            } else {
                showSnackBar("Request failed!")
            }
        }
        homeViewModel.uploadPictureState.observe(viewLifecycleOwner) {
            if (it) {
                requestedBook.picture = homeViewModel.picture
                homeViewModel.requestBook(requestedBook)
            } else {
                loadingDialog.dismiss()
                showSnackBar("Error when request")
            }
        }
    }

    private val onRequest: (requestedBook: RequestedBook) -> Unit = {
        val contentType = getContentType(dialog.uri!!)
        val requestBody = FileRequestBody(
            requireActivity().contentResolver.openInputStream(dialog.uri!!)!!,
            contentType
        );
        val imageBody: MultipartBody.Part =
            MultipartBody.Part.createFormData("file", "file", requestBody)
        loadingDialog = LoadingDialog(requireContext())
        loadingDialog.show()
        requestedBook = it
        homeViewModel.uploadPicture(imageBody)
    }

    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            resultLauncher.launch(it)
        }
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                dialog.uri = result?.data!!.data!!
                dialog.setPicture()
            }
        }

    private fun showSnackBar(msg: String) {
        Snackbar.make(
            requestedBinding.fabAdd,
            msg,
            Snackbar.LENGTH_LONG
        ).also { snackbar ->
            snackbar.setAction("Ok") {
                snackbar.dismiss()
            }
        }.show()
    }

}