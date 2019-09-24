package com.fenchtose.portsadapterdemo.driven.images

import com.fenchtose.portsadapterdemo.network.NetworkError
import com.fenchtose.portsadapterdemo.network.NetworkPort
import com.fenchtose.portsadapterdemo.network.NetworkResult
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.RuntimeException

class FlickrSearchTests {

    private val port = mock<NetworkPort> {
        on { get<FlickrFeed>(eq("rest"), any()) } doAnswer {
            val params = it.getArgument<Map<String, String>>(1)
            if (params["text"] == "batman") {
                NetworkResult.success(
                    FlickrFeed(
                        images = listOf(
                            FlickrImage(
                                id = "1",
                                farm = "1",
                                secret = "1",
                                server = "1"
                            )
                        )
                    )
                )
            } else {
                NetworkResult.failure(NetworkError.GenericError(RuntimeException("invalid query")))
            }
        }
    }

    private val flickrSearch = FlickrSearch(port, "secret")

    @Test
    fun `successful response`() {
        val result = flickrSearch.search("batman")

        verify(port).get<FlickrFeed>(
            "rest",
            mapOf(
                "method" to "flickr.photos.search",
                "format" to "json",
                "jsonCallback" to "1",
                "text" to "batman",
                "api_key" to "secret",
                "page" to "1",
                "per_page" to "20"
            )
        )
        assertEquals("response is success", 1, result.size)
    }

    @Test
    fun `error response`() {
        val result = flickrSearch.search("")
        assertEquals("response is error", 0, result.size)
    }
}