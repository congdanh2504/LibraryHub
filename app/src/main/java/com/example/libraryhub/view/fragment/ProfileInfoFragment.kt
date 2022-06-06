package com.example.libraryhub.view.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.libraryhub.databinding.FragmentProfileInfoBinding
import com.example.libraryhub.model.FileRequestBody
import com.example.libraryhub.model.RequestedBook
import com.example.libraryhub.model.User
import com.example.libraryhub.utils.AppPreferences
import com.example.libraryhub.view.activity.LoginActivity
import com.example.libraryhub.view.dialog.LoadingDialog
import com.example.libraryhub.view.dialog.RequestDialog
import com.example.libraryhub.view.dialog.UpdateProfileDialog
import com.example.libraryhub.viewmodel.ProfileInfoViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.onesignal.OneSignal
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MultipartBody
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ProfileInfoFragment : Fragment() {
    private lateinit var fragmentProfileInfoBinding: FragmentProfileInfoBinding
    private val profileInfoViewModel: ProfileInfoViewModel by viewModels()
    private lateinit var dialog: UpdateProfileDialog
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var updateUser: User
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentProfileInfoBinding = FragmentProfileInfoBinding.inflate(inflater,container,false)
        initActions()
        initObserver()
        return fragmentProfileInfoBinding.root
    }

    private fun initActions() {
        fragmentProfileInfoBinding.signOutButton.setOnClickListener {
            if (OneSignal.getDeviceState() != null) {
                profileInfoViewModel.deleteDeviceId(OneSignal.getDeviceState()!!.userId)
                profileInfoViewModel.deleteState.observe(viewLifecycleOwner) {
                    AppPreferences.JWT = ""
                    profileInfoViewModel.saveCart(arrayListOf())
                    startActivity(Intent(context, LoginActivity::class.java))
                    activity?.finish()
                }
            } else {
                AppPreferences.JWT = ""
                profileInfoViewModel.saveCart(arrayListOf())
                startActivity(Intent(context, LoginActivity::class.java))
                activity?.finish()
            }
        }
        fragmentProfileInfoBinding.updateProfileBtn.setOnClickListener {
            dialog = UpdateProfileDialog(requireContext(), onUpdateProfile, onImageClick)
            dialog.show()
        }
    }

    private fun initObserver() {
        profileInfoViewModel.dataStoreUser.observe(viewLifecycleOwner) {
            val gson = Gson()
            user = gson.fromJson(it, User::class.java)
            fragmentProfileInfoBinding.email.text = user!!.email
            fragmentProfileInfoBinding.username.text = user.username

            if (!user.isExpire()) {
                fragmentProfileInfoBinding.currentPackage.text = user.currentPackage!!.name
                val dateFormatter = SimpleDateFormat("EEE, MMM d, yyyy HH:mm", Locale.getDefault())
                fragmentProfileInfoBinding.expiration.text = dateFormatter.format(user.expiration!!)
            }
            if (user.isBorrowing) {
                fragmentProfileInfoBinding.state.text = "Is borrowing"
            } else {
                fragmentProfileInfoBinding.state.text = "Isn't borrowing"
            }
        }
        profileInfoViewModel.uploadPictureState.observe(viewLifecycleOwner) {
            if (it) {
                updateUser.picture = profileInfoViewModel.picture
                profileInfoViewModel.updateProfile(updateUser)
            } else {
                loadingDialog.dismiss()
                showSnackBar("Error when request")
            }
        }
        profileInfoViewModel.updateProfileState.observe(viewLifecycleOwner) {
            loadingDialog.dismiss()
            if (it) {
                showSnackBar("Update successfully")
            } else {
                showSnackBar("Update failed")
            }
        }
    }

    private val onImageClick: () -> Unit = {
        openImageChooser()
    }

    private val onUpdateProfile: (username: String) -> Unit = {
        val contentType = getContentType(dialog.uri!!)
        val requestBody = FileRequestBody(
            requireActivity().contentResolver.openInputStream(dialog.uri!!)!!,
            contentType
        );
        val imageBody: MultipartBody.Part =
            MultipartBody.Part.createFormData("file", "file", requestBody)
        loadingDialog = LoadingDialog(requireContext())
        loadingDialog.show()
        updateUser = User(
            user._id,
            user.currentPackage,
            user.expiration,
            user.email,
            user.picture,
            user.role,
            it,
            user.isBorrowing
        )
        profileInfoViewModel.uploadPicture(imageBody)
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
            fragmentProfileInfoBinding.textView16,
            msg,
            Snackbar.LENGTH_LONG
        ).also { snackbar ->
            snackbar.setAction("Ok") {
                snackbar.dismiss()
            }
        }.show()
    }
}