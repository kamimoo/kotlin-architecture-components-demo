package com.github.kamimoo.kotlinarchtecture.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.kamimoo.kotlinarchtecture.data.model.Item
import com.github.kamimoo.kotlinarchtecture.databinding.ListItemBinding


class RecyclerViewBindingAdapter : RecyclerView.Adapter<RecyclerViewBindingAdapter.BindingViewHolder>() {

    private var items: List<Item> = emptyList()

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val item = items[position]
        holder.binding.item = item
        holder.binding.executePendingBindings()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BindingViewHolder(parent)

    override fun getItemCount() = items.size

    inner class BindingViewHolder private constructor(val binding: ListItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        constructor(parent: ViewGroup) :
            this(ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    fun update(items: List<Item>) {
        this.items = items
        notifyDataSetChanged()
    }
}
