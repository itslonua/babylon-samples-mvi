package com.babylone.playbook.core.mvp

import android.support.v7.widget.RecyclerView

abstract class AbstractRecyclerViewAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected val items = mutableListOf<T>()

    fun swap(newDataset: List<T>) {
        items.clear()
        items.addAll(newDataset)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

}