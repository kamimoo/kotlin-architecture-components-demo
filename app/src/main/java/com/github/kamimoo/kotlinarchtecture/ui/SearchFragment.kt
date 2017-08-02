package com.github.kamimoo.kotlinarchtecture.ui

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.kamimoo.kotlinarchtecture.databinding.FragmentSearchBinding
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)
        val adapter = RecyclerViewBindingAdapter()
        binding.itemList.adapter = adapter

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
}
