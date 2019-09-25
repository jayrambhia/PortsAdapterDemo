package com.fenchtose.portsadapterdemo.image_loader

import android.widget.ImageView

interface ImageLoaderPort {
    fun loadSearchedImage(view: ImageView, url: String)
}