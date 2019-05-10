package com.babylone.playbook.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.babylone.playbook.core.mvp.AbstractRecyclerViewAdapter

internal class MainActivityAdapter(val callback: (Int) -> Unit) : AbstractRecyclerViewAdapter<MainItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewHolderType: Int): RecyclerView.ViewHolder {
        return MainActivityViewHolder.inflate(LayoutInflater.from(parent.context), parent)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        val mainViewHolder = viewHolder as MainActivityViewHolder
        mainViewHolder.bindTitle(item.title)
        mainViewHolder.setOnClickListener(object : MainActivityViewHolder.OnClickListener {
            override fun onClicked(viewHolder: MainActivityViewHolder) {
                val adapterPosition = viewHolder.adapterPosition
                val selectedItem = items[adapterPosition]
                callback.invoke(selectedItem.postId)
            }
        })
    }
}