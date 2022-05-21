package com.example.libraryhub.view.fragment

import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryhub.adapter.BorrowerAdapter
import com.example.libraryhub.databinding.FragmentBorrowingBinding
import com.example.libraryhub.view.dialog.QRCodeDialog
import com.example.libraryhub.viewmodel.HomeViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class BorrowingFragment : Fragment() {
    private lateinit var borrowingBinding: FragmentBorrowingBinding
    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var adapter: BorrowerAdapter
    private lateinit var _id: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        borrowingBinding = FragmentBorrowingBinding.inflate(inflater, container, false)
        initActions()
        adapter = BorrowerAdapter()
        borrowingBinding.borrowingRecycler.layoutManager = LinearLayoutManager(context)
        borrowingBinding.borrowingRecycler.adapter = adapter
        homeViewModel.getBorrowingBooks()
        initObserver()

        return borrowingBinding.root
    }

    private fun initActions() {
        borrowingBinding.swipeToRefresh.setOnRefreshListener {
            homeViewModel.getBorrowingBooks()
            borrowingBinding.swipeToRefresh.isRefreshing = false
        }
        borrowingBinding.fabQr.setOnClickListener {
            val writer = QRCodeWriter()
            try {
                val bitMatrix = writer.encode(_id, BarcodeFormat.QR_CODE, 512, 512)
                val width = bitMatrix.width
                val height = bitMatrix.height
                val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
                for (x in 0 until width) {
                    for (y in 0 until height) {
                        bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                    }
                }
                val dialog = QRCodeDialog(requireContext(), bmp)
                dialog.show()
            } catch (e: WriterException) {
                e.printStackTrace()
            }
        }
        borrowingBinding.fabReturn.setOnClickListener {
            val alertDialog = AlertDialog.Builder(context)
                .setTitle("Returning confirm")
                .setMessage("Do you want to return?")
                .setCancelable(false)
                .setPositiveButton("Yes"
                ) { _, _ ->
                    homeViewModel.returnBooks(homeViewModel.borrowerRecord.value!!._id)
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.cancel()
                }
                .create()
            alertDialog.show()
        }
    }

    private fun initObserver() {
        homeViewModel.borrowerRecord.observe(viewLifecycleOwner) {
            borrowingBinding.fabQr.visibility = View.GONE
            borrowingBinding.borrowRecord.visibility = View.GONE
            borrowingBinding.borrowingRecycler.visibility = View.GONE
            borrowingBinding.fabReturn.visibility = View.GONE
            borrowingBinding.emptyImage.visibility = View.VISIBLE
            borrowingBinding.emptyText.visibility = View.VISIBLE
            if (it != null) {
                borrowingBinding.borrowRecord.visibility = View.VISIBLE
                borrowingBinding.borrowingRecycler.visibility = View.VISIBLE
                borrowingBinding.emptyImage.visibility = View.GONE
                borrowingBinding.emptyText.visibility = View.GONE
                borrowingBinding.status.text = it.status
                _id = it._id
                val dateFormatter = SimpleDateFormat("EEE, MMM d, yyyy HH:mm", Locale.getDefault())
                borrowingBinding.createdDate.text = dateFormatter.format(it.createdDate)
                borrowingBinding.returnDate.text = dateFormatter.format(it.returnDate)
                if (it.status.startsWith("Pending")) borrowingBinding.fabQr.visibility = View.VISIBLE
                if (it.status == "Borrowing") borrowingBinding.fabReturn.visibility = View.VISIBLE
                adapter.setBooks(it.books)
            }
        }
    }

}