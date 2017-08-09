package com.github.kamimoo.kotlinarchtecture.ui

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.support.customtabs.CustomTabsIntent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.github.kamimoo.kotlinarchtecture.data.model.Item
import com.github.kamimoo.kotlinarchtecture.databinding.FragmentSearchBinding
import com.github.kamimoo.kotlinarchtecture.util.buildWithChromePackage
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class SearchFragment : LifecycleFragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    @Inject
    lateinit var viewModelFactory: SearchViewModelFactory

    private lateinit var viewModel: SearchViewModel

    lateinit var binding: FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        FragmentSearchBinding.inflate(inflater, container, false).also {
            binding = it
        }.root

    fun navigate(item: Item) {
        CustomTabsIntent
            .Builder()
            .buildWithChromePackage(activity)
            .launchUrl(activity, Uri.parse(item.url))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)
        val adapter = RecyclerViewBindingAdapter { item -> navigate(item) }
        binding.itemList.adapter = adapter

        initSearchInputListener()

        binding.itemList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition == adapter.itemCount - 1) {
                    viewModel.loadNextPage()
                }
            }
        })

        viewModel.items.observe(this, Observer {
            it?.let {
                adapter.update(it)
            }
        })

        viewModel.isLoading.observe(this, Observer {
            it?.let {
                binding.isLoading = it
            }
        })

    }

    private fun initSearchInputListener() {
        binding.input.apply {
            setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER) {
                    doSearch(v)
                    true
                }
                false
            }
            setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    doSearch(v)
                    true
                }
                false
            }
        }
    }

    private fun doSearch(v: View) {
        val query = binding.input.text.toString()
        dismissKeyboard(v.windowToken)
        viewModel.query = query
    }

    private fun dismissKeyboard(windowToken: IBinder) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
