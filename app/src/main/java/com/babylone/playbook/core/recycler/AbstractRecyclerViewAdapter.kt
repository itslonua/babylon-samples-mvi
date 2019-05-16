package com.babylone.playbook.core.recycler

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView

abstract class AbstractRecyclerViewAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected var items = mutableListOf<T>()

    fun swap(items: List<T>, diffUtilsCallback: (List<T>, List<T>) -> DiffUtil.Callback) {
        val diffResult = DiffUtil.calculateDiff(diffUtilsCallback.invoke(this.items, items))
        this.items = items.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return items.size
    }

}