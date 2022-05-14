package com.example.libraryhub.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.libraryhub.view.fragment.BookListFragmentArgs
import com.example.libraryhub.databinding.FragmentBookListBinding

class BookListFragment : Fragment() {
    private lateinit var bookListBinding: FragmentBookListBinding
    val args: BookListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bookListBinding = FragmentBookListBinding.inflate(inflater, container, false)

        Log.d("AAA", args.categoryId)
//        Toast.makeText(context, args.categoryId, Toast.LENGTH_LONG).show()

        return bookListBinding.root
    }

}