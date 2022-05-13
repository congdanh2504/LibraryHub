package com.example.libraryhub.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryhub.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var homeBinding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var expand  = false
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
//        homeBinding.RecyclerViewBorrowing.layoutManager = LinearLayoutManager(context)
//        val data = ArrayList<Book>()
//        for (i in 1..5) {
//            data.add(Book("123","this is the title","https://product.hstatic.net/200000343865/product/6_90462943d6e348c8a807fddc3f0d688b_master.jpg","this is description","this is author","publisher",123456,
//                Location("1","2","3")
//            ))
//        }
//
//
//        homeBinding.RecyclerViewBorrowing.adapter = AdapterBorrowing(data)
//        homeBinding.textViewBr.setOnClickListener{
//            expand = !expand;
//            activity?.runOnUiThread{
//                homeBinding.expandedViewBr.visibility = if(expand) View.VISIBLE else View.GONE
//            }
//        }
//        homeBinding.RecyclerViewRecent.adapter = AdapterBorrowing(data)
//        homeBinding.RecyclerViewRecent.layoutManager = LinearLayoutManager(context)
//        homeBinding.textViewRc.setOnClickListener{
//            expand = !expand;
//            activity?.runOnUiThread{
//                homeBinding.expandedViewRc.visibility = if(expand) View.VISIBLE else View.GONE
//            }
//        }
//        homeBinding.RecyclerViewRequest.adapter = AdapterBorrowing(data)
//        homeBinding.RecyclerViewRequest.layoutManager = LinearLayoutManager(context)
//        homeBinding.textViewRq.setOnClickListener{
//            expand = !expand;
//            activity?.runOnUiThread{
//                homeBinding.expandedViewRq.visibility = if(expand) View.VISIBLE else View.GONE
//            }
//        }


        return homeBinding.root
    }
}