package com.hampson.booksearchapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hampson.booksearchapp.data.model.Book
import com.hampson.booksearchapp.data.repository.BookSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val bookSearchRepository: BookSearchRepository,
) : ViewModel() {

    // Room
    fun saveBook(book: Book) = viewModelScope.launch(Dispatchers.IO) {
        bookSearchRepository.insertBooks(book)
    }
}