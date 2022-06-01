package com.hampson.booksearchapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import com.hampson.booksearchapp.data.repository.BookSearchRepository

class BookSearchViewModelProviderFactory(
    private val bookSearchRepository: BookSearchRepository,
    private val workManager: WorkManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookSearchViewModel::class.java)) {
            return BookSearchViewModel(bookSearchRepository, workManager) as T
        }
        throw IllegalAccessException("ViewModel class not found")
    }
}

//class BookSearchViewModelProviderFactory(
//    private val bookSearchRepository: BookSearchRepository,
//    owner: SavedStateRegistryOwner,
//    defaultArgs: Bundle? = null,
//) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
//    override fun <T : ViewModel?> create(
//        key: String,
//        modelClass: Class<T>,
//        handle: SavedStateHandle
//    ): T {
//        if (modelClass.isAssignableFrom(BookSearchViewModel::class.java)) {
//            return BookSearchViewModel(bookSearchRepository, handle) as T
//        }
//        throw IllegalArgumentException("ViewModel class not found")
//    }
//}