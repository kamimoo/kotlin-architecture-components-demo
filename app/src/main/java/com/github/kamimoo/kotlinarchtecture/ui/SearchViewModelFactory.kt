package com.github.kamimoo.kotlinarchtecture.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.github.kamimoo.kotlinarchtecture.data.repository.ItemRepository
import javax.inject.Inject


class SearchViewModelFactory
@Inject constructor(val repository: ItemRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
        return SearchViewModel(repository) as T
    }
}
