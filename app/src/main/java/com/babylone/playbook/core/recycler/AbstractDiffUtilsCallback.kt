package com.babylone.playbook.core.recycler

import android.support.v7.util.DiffUtil

abstract class AbstractDiffUtilsCallback<T>(
        private val previousItems: List<T>,
        private val newItems: List<T>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = previousItems[oldItemPosition]
        val newItem = newItems[newItemPosition]

        return isSameItems(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = previousItems[oldItemPosition]
        val newItem = newItems[newItemPosition]

        return isSameContent(oldItem, newItem)
    }

    override fun getOldListSize(): Int = previousItems.size

    override fun getNewListSize(): Int = newItems.size

    abstract fun isSameItems(newItem: T?, oldItem: T?): Boolean

    abstract fun isSameContent(newItem: T?, oldItem: T?): Boolean
}