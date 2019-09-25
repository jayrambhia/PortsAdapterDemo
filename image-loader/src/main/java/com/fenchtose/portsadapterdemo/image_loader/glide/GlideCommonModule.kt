package com.fenchtose.portsadapterdemo.image_loader.glide

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class GlideCommonModule : AppGlideModule() {
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}