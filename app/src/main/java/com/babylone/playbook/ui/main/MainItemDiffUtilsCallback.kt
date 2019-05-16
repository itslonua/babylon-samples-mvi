package com.babylone.playbook.ui.main

import com.babylone.playbook.core.recycler.AbstractDiffUtilsCallback

class MainItemDiffUtilsCallback(
        previousItems: List<MainItem>,
        newItems: List<MainItem>
) : AbstractDiffUtilsCallback<MainItem>(previousItems, newItems) {

    override fun isSameItems(newItem: MainItem?, oldItem: MainItem?): Boolean {
        return newItem?.postId == oldItem?.postId
    }

    override fun isSameContent(newItem: MainItem?, oldItem: MainItem?): Boolean {
        return newItem?.title == oldItem?.title
    }

}