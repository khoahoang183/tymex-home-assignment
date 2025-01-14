package com.khoahoang183.basesource.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.khoahoang183.basesource.common.extension.singleClick


typealias InflateAlias<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class ListAdapter<T : Any, VB : ViewBinding>(
    mInflate: InflateAlias<VB>,
    itemsSame: (T, T) -> Boolean,
    contentsSame: (T, T) -> Boolean,
    changePayload: ((T, T) -> Boolean)? = null
) : BaseListAdapter<T, VB>(mInflate, itemsSame, contentsSame) {

    override fun bindViewHolder(binding: VB, model: T, position: Int, viewType: Int) {
        // Nothing
    }

    override fun submitList(list: List<T>?, commitCallback: Runnable?) {
        super.submitList(list?.let { ArrayList(it) }) {
            onAdapterChanged?.invoke()
            commitCallback?.run()
        }
    }

    override fun submitList(list: List<T>?) {
        this.submitList(list, null)
    }

    var onAdapterChanged: (() -> Unit)? = null
}

abstract class BaseListAdapter<T : Any, VB : ViewBinding>(
    private val mInflate: InflateAlias<VB>,
    itemsSame: (T, T) -> Boolean,
    contentsSame: (T, T) -> Boolean,
    changePayload: ((T, T) -> Boolean)? = null
) : ListAdapter<T, BaseListAdapter<T, VB>.BaseViewHolder>(object : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(old: T, new: T): Boolean = itemsSame(old, new)
    override fun areContentsTheSame(old: T, new: T): Boolean = contentsSame(old, new)
    override fun getChangePayload(old: T, new: T): Boolean {
        return changePayload?.let { it(old, new) } ?: run { true }
    }
}) {

    protected var onPositionClickListener: ((Int) -> Unit)? = null
    protected var onItemClickListener: ((T) -> Unit)? = null

    protected abstract fun bindViewHolder(binding: VB, model: T, position: Int, viewType: Int)

    fun onPositionClick(callback: (Int) -> Unit) {
        this.onPositionClickListener = callback
    }

    open fun onItemClick(callback: (T) -> Unit) {
        this.onItemClickListener = callback
    }


    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = mInflate.invoke(LayoutInflater.from(parent.context), parent, false)
        return BaseViewHolder(binding, viewType)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    open inner class BaseViewHolder(var binding: VB, var viewType: Int) :
        RecyclerView.ViewHolder(binding.root) {

        open fun bind(model: T, position: Int) {
            itemView.singleClick {
                val pos = this@BaseViewHolder.adapterPosition
                if (pos > RecyclerView.NO_POSITION) {
                    onPositionClickListener?.invoke(pos)
                    onItemClickListener?.invoke(model)
                }
            }
            bindViewHolder(binding, model, position, viewType)
        }
    }
}