package com.fenchtose.portsadapterdemo.driven.images

import com.fenchtose.portsadapterdemo.hexagon.images.SearchImage
import com.fenchtose.portsadapterdemo.hexagon.images.ImageSearchDrivenPort
import com.fenchtose.portsadapterdemo.network.NetworkPort
import com.fenchtose.portsadapterdemo.network.optSuccess

class FlickrSearch(private val port: NetworkPort, private val apiKey: String) : ImageSearchDrivenPort {
    override fun search(query: String): List<SearchImage>? {
        val result = port.get(
            FlickrResponse::class.java,
            "rest",
            mapOf(
                "method" to "flickr.photos.search",
                "format" to "json",
                "nojsoncallback" to "1",
                "text" to query,
                "api_key" to apiKey,
                "page" to "1",
                "per_page" to "20"
            )
        )

        return result.optSuccess()?.feed?.images?.flickrToModel()
    }
}