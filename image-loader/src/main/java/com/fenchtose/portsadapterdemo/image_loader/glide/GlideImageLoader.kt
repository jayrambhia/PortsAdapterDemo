package com.fenchtose.portsadapterdemo.image_loader.glide

import android.widget.ImageView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fenchtose.portsadapterdemo.image_loader.ImageLoaderPort

class GlideImageLoader : ImageLoaderPort {
    override fun loadSearchedImage(view: ImageView, url: String) {
        GlideApp.with(view)
            .load(url)
            .centerCrop()
            .thumbnail(0.2f)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}