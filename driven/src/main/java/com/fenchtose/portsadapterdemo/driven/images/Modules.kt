package com.fenchtose.portsadapterdemo.driven.images

import com.fenchtose.portsadapterdemo.hexagon.images.ImageSearchDrivenPort
import com.fenchtose.portsadapterdemo.network.NetworkPort
import dagger.Module
import dagger.Provides

@Module
class ImageSearchDrivenModule(private val apiKey: String) {
    @Provides
    fun adapter(port: NetworkPort): ImageSearchDrivenPort {
        return FlickrSearch(port, apiKey)
    }
}