package com.babylone.playbook.core

import android.widget.ImageView

interface ImageLoaderProxy {

    fun bind(imageView: ImageView, imageUrl: String)

}