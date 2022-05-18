package com.example.libraryhub.view.fragment

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryhub.adapter.AdapterBorrow
import com.example.libraryhub.databinding.FragmentBorrowingBinding
import com.example.libraryhub.view.dialog.CustomDialog
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
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var adapter: AdapterBorrow
    private lateinit var _id: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        borrowingBinding = FragmentBorrowingBinding.inflate(inflater, container, false)
        initActions()
        adapter = AdapterBorrow()
        borrowingBinding.borrowingRecycler.layoutManager = LinearLayoutManager(context)
        borrowingBinding.borrowingRecycler.adapter = adapter
        homeViewModel._borrowerRecord.postValue(null)
        homeViewModel.getBorrowingBooks()

        homeViewModel.borrowerRecord.observe(viewLifecycleOwner) {
            if (it != null) {
                borrowingBinding.borrowRecord.visibility = View.VISIBLE
                borrowingBinding.borrowingRecycler.visibility = View.VISIBLE
                borrowingBinding.emptyImage.visibility = View.GONE
                borrowingBinding.emptyText.visibility = View.GONE
                borrowingBinding.status.text = it.status
                _id = it._id
                val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                borrowingBinding.createdDate.text = dateFormatter.format(it.createdDate)
                borrowingBinding.returnDate.text = dateFormatter.format(it.returnDate)
                if (!it.status.startsWith("Pending")) borrowingBinding.qr.visibility = View.GONE
                else borrowingBinding.qr.visibility = View.VISIBLE
                adapter.setBooks(it.books)
            } else {
                borrowingBinding.borrowRecord.visibility = View.GONE
                borrowingBinding.borrowingRecycler.visibility = View.GONE
                borrowingBinding.emptyImage.visibility = View.VISIBLE
                borrowingBinding.emptyText.visibility = View.VISIBLE
            }
        }

        return borrowingBinding.root
    }

    private fun initActions() {
        borrowingBinding.swipeToRefresh.setOnRefreshListener {
            homeViewModel._borrowerRecord.postValue(null)
            homeViewModel.getBorrowingBooks()
            borrowingBinding.swipeToRefresh.isRefreshing = false
        }
        borrowingBinding.qrIcon.setOnClickListener {
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
                val dialog = CustomDialog(requireContext(), bmp)
                dialog.show()
            } catch (e: WriterException) {
                e.printStackTrace()
            }
        }
    }

}