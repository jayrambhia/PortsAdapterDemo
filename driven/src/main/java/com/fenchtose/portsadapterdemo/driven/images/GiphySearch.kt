package com.fenchtose.portsadapterdemo.driven.images

import com.fenchtose.portsadapterdemo.hexagon.images.ImageSearchDrivenPort
import com.fenchtose.portsadapterdemo.hexagon.images.SearchImage
import com.fenchtose.portsadapterdemo.network.NetworkPort
import com.fenchtose.portsadapterdemo.network.optFailure
import com.fenchtose.portsadapterdemo.network.optSuccess

class GiphySearch(private val port: NetworkPort, private val apiKey: String) : ImageSearchDrivenPort {
    override fun search(query: String): List<SearchImage>? {
        val result = port.get(
            GiphyResponse::class.java,
            "search",
            mapOf(
                "q" to query,
                "api_key" to apiKey,
                "limit" to "20",
                "offset" to "0"
            )
        )

        return result.optSuccess()?.images?.giphyToModel()
    }
}