package com.github.kamimoo.kotlinarchtecture.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.github.kamimoo.kotlinarchtecture.data.model.Item
import com.github.kamimoo.kotlinarchtecture.data.repository.ItemRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class SearchViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val _items: MutableLiveData<List<Item>> = MutableLiveData()
    val items: LiveData<List<Item>> = _items

    var nextPage: Int? = null

    init {
        reloadData("")
    }

    private fun reloadData(input: String) {
        disposable.add(itemRepository.getItems(input)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { (items, page) ->
                    _items.value = items
                    this.nextPage = page
                },
                { _items.value = emptyList() }
            ))
    }

    override fun onCleared() {
        disposable.clear()
    }
}
