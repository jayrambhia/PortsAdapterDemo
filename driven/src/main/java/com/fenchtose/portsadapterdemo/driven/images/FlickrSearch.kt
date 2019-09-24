package com.fenchtose.portsadapterdemo.driven.images

import com.fenchtose.portsadapterdemo.hexagon.images.SearchImage
import com.fenchtose.portsadapterdemo.hexagon.images.SearchImagesDrivenPort
import com.fenchtose.portsadapterdemo.network.NetworkPort
import com.fenchtose.portsadapterdemo.network.optSuccess

class FlickrSearch(private val port: NetworkPort, private val apiKey: String) : SearchImagesDrivenPort {
    override fun search(query: String): List<SearchImage>? {
        val result = port.get<FlickrFeed>(
            "rest",
            mapOf(
                "method" to "flickr.photos.search",
                "format" to "json",
                "jsonCallback" to "1",
                "text" to query,
                "api_key" to apiKey,
                "page" to "1",
                "per_page" to "20"
            )
        )

        return result.optSuccess()?.images?.toModel()
    }
}