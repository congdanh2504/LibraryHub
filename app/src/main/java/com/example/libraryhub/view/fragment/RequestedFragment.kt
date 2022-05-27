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
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.libraryhub.adapter.RequestedAdapter
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
    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var dialog: RequestDialog
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var requestedBook: RequestedBook
    private lateinit var adapter: RequestedAdapter
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requestedBinding = FragmentRequestedBinding.inflate(inflater, container, false)
        adapter = RequestedAdapter(requireContext(), onDelete)
        requestedBinding.requestedRecycler.layoutManager = LinearLayoutManager(context)
        requestedBinding.requestedRecycler.adapter = adapter
        homeViewModel.getRequestedBooks()
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
        requestedBinding.swipeToRefresh.setOnRefreshListener {
            homeViewModel.refreshRequestedBooks()
            requestedBinding.swipeToRefresh.isRefreshing = false
        }

        requestedBinding.requestedRecycler.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE && !isLoading) {
                    requestedBinding.progressBar.visibility = View.VISIBLE
                    isLoading = true
                    homeViewModel.getRequestedBooks()
                }
            }
        })
    }

    private val onDelete : (bookId: String) -> Unit = {
        homeViewModel.deleteRequestedBook(it)
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
        homeViewModel.requestedBooks.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                requestedBinding.requestedRecycler.visibility = View.VISIBLE
                requestedBinding.emptyImage.visibility = View.GONE
                requestedBinding.emptyText.visibility = View.GONE
                adapter.setBook(it)
            } else if (!isLoading) {
                requestedBinding.requestedRecycler.visibility = View.GONE
                requestedBinding.emptyImage.visibility = View.VISIBLE
                requestedBinding.emptyText.visibility = View.VISIBLE
            }
            isLoading = false
            requestedBinding.progressBar.visibility = View.GONE
        }
        homeViewModel.categories.observe(viewLifecycleOwner) {
            initActions()
        }
        homeViewModel.requestState.observe(viewLifecycleOwner) {
            loadingDialog.dismiss()
            if (it) {
                homeViewModel.refreshRequestedBooks()
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