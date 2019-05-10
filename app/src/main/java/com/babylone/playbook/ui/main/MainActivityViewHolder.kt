package com.babylone.playbook.ui.main

import android.support.annotation.NonNull
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.babylone.playbook.R
import kotlinx.android.synthetic.main.main_activity_viewholder.view.*

internal class MainActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {

        fun inflate(layoutInflater: LayoutInflater, parent: ViewGroup): MainActivityViewHolder {
            return MainActivityViewHolder(
                layoutInflater.inflate(R.layout.main_activity_viewholder, parent, false)
            )
        }

    }

    interface OnClickListener {

        fun onClicked(viewHolder: MainActivityViewHolder)

    }

    init {
        itemView.setOnClickListener { onClick() }
    }

    private var clickListener: OnClickListener? = null

    fun bindTitle(@NonNull title: String) {
        itemView.main_activity_viewholder_title.text = title
    }

    fun setOnClickListener(callback: OnClickListener) {
        clickListener = callback
    }

    private fun onClick() {
        clickListener?.onClicked(this)
    }

}
