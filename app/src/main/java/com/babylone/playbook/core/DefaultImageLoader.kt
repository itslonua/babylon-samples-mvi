package com.babylone.playbook.core

import android.widget.ImageView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy

class DefaultImageLoader(private val requestManager: RequestManager) : ImageLoaderProxy {

    override fun bind(imageView: ImageView, imageUrl: String) {
        requestManager.load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }

}