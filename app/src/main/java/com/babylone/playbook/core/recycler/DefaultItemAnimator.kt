package com.babylone.playbook.core.recycler

import android.support.v7.widget.DefaultItemAnimator


class DefaultItemAnimatorNoChange : DefaultItemAnimator() {

    init {
        supportsChangeAnimations = false
    }

}