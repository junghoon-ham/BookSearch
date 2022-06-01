package com.hampson.booksearchapp.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.hampson.booksearchapp.databinding.FragmentBookBinding
import com.hampson.booksearchapp.ui.viewModel.BookViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookFragment : Fragment() {
    private var _binding: FragmentBookBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<BookFragmentArgs>()

    //private lateinit var bookSearchViewModel: BookSearchViewModel
    //private val bookSearchViewModel by activityViewModels<BookSearchViewModel>()
    private val bookViewModel by viewModels<BookViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //bookSearchViewModel = (activity as MainActivity).bookSearchViewModel

        val book = args.book
        binding.webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(book.url)
        }

        binding.fabFavorite.setOnClickListener {
            bookViewModel.saveBook(book)
            Snackbar.make(view, "저장 완료", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onPause() {
        binding.webView.onPause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.webView.onResume()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}