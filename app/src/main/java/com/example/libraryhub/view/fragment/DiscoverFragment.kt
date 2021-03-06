package com.example.libraryhub.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryhub.adapter.DiscoverParentAdapter
import com.example.libraryhub.databinding.FragmentDiscoverBinding
import com.example.libraryhub.model.*
import com.example.libraryhub.viewmodel.DiscoverViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverFragment : Fragment() {
    private lateinit var disBinding: FragmentDiscoverBinding
    private val discoverViewModel: DiscoverViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        disBinding = FragmentDiscoverBinding.inflate(inflater, container, false)
        disBinding.DiscoverRecyclerView.layoutManager = LinearLayoutManager(context)
        discoverViewModel.getDiscover()
        discoverViewModel.discover.observe(viewLifecycleOwner) { it ->
            disBinding.progressBar.visibility = View.GONE
            val data: List<DiscoverParent> = listOf(DiscoverParent("Top 10 highest rate books", it[0]),
                DiscoverParent("Top 10 highest borrowed number books", it[1]),
                DiscoverParent("Top 10 highest review books", it[2].sortedByDescending { it.reviews.size }))
            disBinding.DiscoverRecyclerView.adapter = DiscoverParentAdapter(data, onBookClick)
        }

        return disBinding.root
    }

    private val onBookClick: (book: Book) -> Unit = { book ->
        val action = DiscoverFragmentDirections.actionDiscoverFragmentToBookDetailFragment(book)
        findNavController().navigate(action)
    }
}