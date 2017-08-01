package com.github.kamimoo.kotlinarchtecture.ui

import android.arch.lifecycle.LifecycleFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.kamimoo.kotlinarchtecture.databinding.FragmentSearchBinding
import dagger.android.support.AndroidSupportInjection


class SearchFragment : LifecycleFragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

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
        binding.itemList.adapter = RecyclerViewBindingAdapter()
    }
}
