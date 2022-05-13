package com.example.libraryhub.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.libraryhub.databinding.ActivitySignUpBinding
import com.example.libraryhub.viewmodel.SignUpViewModel
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
            val username = signUpBinding.username.text.toString()
            val email = signUpBinding.email.text.toString()
            val password = signUpBinding.password.text.toString()
            val confirmPassword = signUpBinding.confirmPassword.text.toString()
            if (password.equals(confirmPassword)) {
                signUpViewModel.signUp(username, email, password)
            } else {
                Toast.makeText(this@SignUpActivity, "Password and confirm password is not the same!", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun initObserver() {
        signUpViewModel.signUpState.observe(this) {
            if (it) {
                Toast.makeText(this@SignUpActivity, "Sign up successfully!", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this@SignUpActivity, "Sign up failed!", Toast.LENGTH_LONG).show()
            }
        }
    }
}