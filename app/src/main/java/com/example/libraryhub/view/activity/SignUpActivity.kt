package com.example.libraryhub.view.activity

import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.libraryhub.databinding.ActivitySignUpBinding
import com.example.libraryhub.viewmodel.SignUpViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private lateinit var signUpBinding: ActivitySignUpBinding
    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(signUpBinding.root)
        initActions()
        initObserver()
    }

    private fun initActions() {
        signUpBinding.textSignIn.setOnClickListener {
            finish()
        }
        signUpBinding.signUpButton.setOnClickListener {
            if (!signUpBinding.email.checkEmpty()) return@setOnClickListener
            if (!signUpBinding.username.checkEmpty()) return@setOnClickListener
            if (!signUpBinding.password.checkEmpty()) return@setOnClickListener
            if (!signUpBinding.confirmPassword.checkEmpty()) return@setOnClickListener
            val username = signUpBinding.username.text.toString()
            val email = signUpBinding.email.text.toString()
            val password = signUpBinding.password.text.toString()
            val confirmPassword = signUpBinding.confirmPassword.text.toString()
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                signUpBinding.email.error = "Must be valid email address"
                return@setOnClickListener
            }
            if (username.length < 5) {
                signUpBinding.username.error = "Username must be greater than 5 characters"
                return@setOnClickListener
            }
            if (password.length < 8) {
                signUpBinding.password.error = "Password must be greater than 8 characters"
                return@setOnClickListener
            }
            if (password != confirmPassword) {
                signUpBinding.password.error = "Must be the same"
                signUpBinding.confirmPassword.error = "Must be the same"
                return@setOnClickListener
            }
            signUpViewModel.signUp(username, email, password)
        }
    }

    private fun initObserver() {
        signUpViewModel.signUpState.observe(this) {
            if (it) {
                showSnackBar("Sign up successfully!")
                finish()
            } else {
                showSnackBar("Sign up failed!")
            }
        }
    }

    private fun EditText.checkEmpty(): Boolean {
        if (this.text.toString().isEmpty()) {
            this.error = "Required!"
            return false
        }
        return true
    }

    private fun showSnackBar(msg: String) {
        Snackbar.make(
            signUpBinding.textView5,
            msg,
            Snackbar.LENGTH_LONG
        ).also { snackbar ->
            snackbar.setAction("Ok") {
                snackbar.dismiss()
            }
        }.show()
    }
}