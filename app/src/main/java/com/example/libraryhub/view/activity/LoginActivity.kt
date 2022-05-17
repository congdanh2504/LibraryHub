package com.example.libraryhub.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.libraryhub.R
import com.example.libraryhub.databinding.ActivityLoginBinding
import com.example.libraryhub.utils.AppPreferences
import com.example.libraryhub.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
        initActions()
        initObserver()
        initGoogle()
    }

    private fun initActions() {
        loginBinding.textSignUp.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
        }
        loginBinding.googleSignIn.setSize(SignInButton.SIZE_STANDARD)
        loginBinding.googleSignIn.setOnClickListener {
            val signInIntent: Intent = mGoogleSignInClient.signInIntent
            resultLauncher.launch(signInIntent)
        }
        loginBinding.signInButton.setOnClickListener {
            val email = loginBinding.email.text.toString()
            val password = loginBinding.password.text.toString()
            loginViewModel.signIn(email, password)
        }
    }

    private fun initObserver() {
        loginViewModel.user.observe(this) {
            AppPreferences.user = it
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
        loginViewModel.loginState.observe(this) {
            if (it) {
                Snackbar.make(
                    loginBinding.imageView3,
                    "Login successfully!",
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                Snackbar.make(
                    loginBinding.imageView3,
                    "Login failed!",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun initGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val loginResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)
            if (loginResult!!.isSuccess) {
                val acct = loginResult.signInAccount
                val idToken = acct!!.idToken
                loginViewModel.signInWithGoogle("$idToken")
            } else {
                Snackbar.make(
                    loginBinding.imageView3,
                    "Error when sign in with google!",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
        mGoogleSignInClient.signOut()
    }
}