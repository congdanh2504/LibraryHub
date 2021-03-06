package com.example.libraryhub.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.onesignal.OneSignal
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
            if (email.isEmpty()) {
                loginBinding.email.error = "Required!"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                loginBinding.password.error = "Required!"
                return@setOnClickListener
            }
            loginViewModel.signIn(email, password)
        }
    }

    private fun initObserver() {
        loginViewModel.user.observe(this) {
            OneSignal.getDeviceState()?.let { it2 -> loginViewModel.addDeviceId(it2.userId) }
            if (it.role == "user") startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            else if (it.role == "admin") startActivity(Intent(this@LoginActivity, ManagerMainActivity::class.java))
            finish()
        }
        loginViewModel.loginState.observe(this) {
            if (it) {
                showSnackBar("Login successfully!")
            } else {
                showSnackBar("Login failed!")
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
                showSnackBar("Error when sign in with google!")
            }
        }
        mGoogleSignInClient.signOut()
    }

    private fun showSnackBar(msg: String) {
        Snackbar.make(
            loginBinding.imageView3,
            msg,
            Snackbar.LENGTH_LONG
        ).show()
    }
}