package com.fenchtose.portsadapterdemo.image_loader.glide

import android.widget.ImageView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.fenchtose.portsadapterdemo.image_loader.ImageLoaderPort

class GlideImageLoader : ImageLoaderPort {
    override fun loadSearchedImage(view: ImageView, url: String, isGif: Boolean) {
        if (isGif) {
            GlideApp.with(view)
                .asGif()
                .load(url)
                .centerCrop()
                .thumbnail(0.2f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
        } else {
            GlideApp.with(view)
                .load(url)
                .centerCrop()
                .thumbnail(0.2f)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
        }

    }
}