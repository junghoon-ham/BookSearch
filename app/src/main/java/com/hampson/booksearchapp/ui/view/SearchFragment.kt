package com.hampson.booksearchapp.ui.view

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hampson.booksearchapp.databinding.FragmentSearchBinding
import com.hampson.booksearchapp.ui.adapter.BookSearchPagingAdapter
import com.hampson.booksearchapp.ui.viewModel.BookSearchViewModel
import com.hampson.booksearchapp.util.Constants.SEARCH_BOOKS_TIME_DELAY
import com.hampson.booksearchapp.util.collectLatestStateFlow

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var bookSearchViewModel: BookSearchViewModel

    //private lateinit var bookSearchAdapter: BookSearchAdapter
    private lateinit var bookSearchPagingAdapter: BookSearchPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookSearchViewModel = (activity as MainActivity).bookSearchViewModel

        setupRecyclerView()
        searchBooks()

        //bookSearchViewModel.searchResult.observe(viewLifecycleOwner) { response ->
        //    val books = response.documents
        //    bookSearchPagingAdapter.submitList(books)
        //}

        collectLatestStateFlow(bookSearchViewModel.searchPagingResult) {
            bookSearchPagingAdapter.submitData(it)
        }
    }

    private fun setupRecyclerView() {
        //bookSearchAdapter = BookSearchAdapter()
        bookSearchPagingAdapter = BookSearchPagingAdapter()
        binding.rvSearchResult.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = bookSearchPagingAdapter
        }
        bookSearchPagingAdapter.setOnItemClickListener {
            val action = SearchFragmentDirections.actionFragmentSearchToFragmentBook(it)
            findNavController().navigate(action)
        }
    }

    private fun searchBooks() {
        var startTime = System.currentTimeMillis()
        var endTime: Long

        binding.etSearch.addTextChangedListener { text: Editable? ->
            endTime = System.currentTimeMillis()
            if (endTime - startTime >= SEARCH_BOOKS_TIME_DELAY) {
                text?.let {
                    val query = it.toString().trim()
                    if (query.isNotEmpty()) {
                        //bookSearchViewModel.searchBooks(query)
                        bookSearchViewModel.searchBooksPaging(query)
                    }
                }
            }
            startTime = endTime
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}