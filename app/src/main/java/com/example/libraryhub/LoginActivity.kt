package com.example.libraryhub

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.libraryhub.databinding.ActivityLoginBinding
import com.example.libraryhub.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val loginViewModel: LoginViewModel by viewModels()
    private val RC_SIGN_IN = 0

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
            val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
        loginBinding.signInButton.setOnClickListener {
            val email = loginBinding.email.text.toString()
            val password = loginBinding.password.text.toString()
            loginViewModel.signIn(email, password)
        }
    }

    private fun initObserver() {
        loginViewModel.user.observe(this) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
        loginViewModel.loginState.observe(this) {
            if (it) {
                Toast.makeText(this@LoginActivity, "Login successfully!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@LoginActivity, "Login failed!", Toast.LENGTH_LONG).show()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)
            if (result!!.isSuccess) {
                val acct = result.signInAccount
                val idToken = acct!!.idToken
                loginViewModel.signInWithGoogle("$idToken")
            } else {
                Toast.makeText(this@LoginActivity, "Error when sign in with google!", Toast.LENGTH_LONG).show()
            }
        }
        mGoogleSignInClient.signOut()
    }
}