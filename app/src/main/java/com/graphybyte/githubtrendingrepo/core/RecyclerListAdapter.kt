package com.graphybyte.githubtrendingrepo.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import com.graphybyte.githubtrendingrepo.BR


class RecyclerListAdapter<T : Any, B : ViewDataBinding>(
    diffUtil: DiffUtil.ItemCallback<T>,
    @LayoutRes private val layoutId: Int,
    private val onItemClickListener: OnItemClickListener,
) : ListAdapter<T, ViewHolder<B>>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<B> {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutId,
                parent,
                false
            ), onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder<B>, position: Int) {
        holder.bind(getItem(position))
    }

}

open class ViewHolder<B : ViewDataBinding>(
    val binding: B,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Any) {
        if (BR.position == BR.data - 1) {
            Timber.d("Last Position")
        }
        binding.setVariable(BR.data, item)
        binding.setVariable(BR.position, bindingAdapterPosition)
        binding.setVariable(BR.callback, onItemClickListener)
        binding.executePendingBindings()
    }
}

interface OnItemClickListener {
    fun onItemClick(data: Any, position: Int, view: View)
}