package com.example.libraryhub.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.libraryhub.R

import com.example.libraryhub.databinding.ActivityManagerMainBinding
import com.example.libraryhub.utils.AppPreferences
import com.example.libraryhub.viewmodel.AdminViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.integration.android.IntentIntegrator
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagerMainActivity : AppCompatActivity() {
    private lateinit var managerMainBinding: ActivityManagerMainBinding
    private lateinit var viewPager: ViewPager2
    private val adminViewModel: AdminViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        managerMainBinding = ActivityManagerMainBinding.inflate(layoutInflater)
        setContentView(managerMainBinding.root)
        val navController = findNavController(R.id.nav_fragment)
        managerMainBinding.bottomNavigatinView.setupWithNavController(navController)
        Picasso.get()
            .load(AppPreferences.user?.picture)
            .placeholder(R.drawable.profileplaceholder)
            .into(managerMainBinding.avatar)
        initActions()
        initObserver()
    }

    private fun initActions() {
        managerMainBinding.fab.setOnClickListener {
            val intentIntegrator = IntentIntegrator(this)
            intentIntegrator.setDesiredBarcodeFormats(listOf(IntentIntegrator.QR_CODE))
            intentIntegrator.initiateScan()
        }
    }

    private fun initObserver() {
        adminViewModel.getRecordState.observe(this) {
            if (it) {
//                val dialog = RecordDialog(this, adminViewModel.record.value!!)
//                dialog.show()
                Intent(this@ManagerMainActivity, ManagerRecordActivity::class.java).also {
                    it.putExtra("record", adminViewModel.record.value!!)
                    startActivity(it)
                }
            } else {
                showSnackBar("This QR Code is invalid")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(resultCode, data)
        result.contents?.let {
            adminViewModel.getRecordById(it)
        }
    }

    private fun showSnackBar(msg: String) {
        Snackbar.make(
            managerMainBinding.cart,
            msg,
            Snackbar.LENGTH_LONG
        ).show()
    }
}