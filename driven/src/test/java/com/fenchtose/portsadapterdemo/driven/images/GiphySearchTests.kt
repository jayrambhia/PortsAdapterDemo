package com.fenchtose.portsadapterdemo.driven.images

import com.fenchtose.portsadapterdemo.network.NetworkError
import com.fenchtose.portsadapterdemo.network.NetworkPort
import com.fenchtose.portsadapterdemo.network.NetworkResult
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.RuntimeException

class GiphySearchTests {
    private val port = mock<NetworkPort> {
        on { get(eq(GiphyResponse::class.java), eq("search"), any()) } doAnswer {
            val params = it.getArgument<Map<String, String>>(2)
            if (params["q"] == "batman") {
                NetworkResult.success(
                    GiphyResponse(
                        images = listOf(
                            GiphyImage(
                                id = "1",
                                slug = "giphy_giphy",
                                url = "giphy_url",
                                bundle = GiphyImageBundle(
                                    fixedWidth = GiphyImageRaw(
                                        url = "giphy_image_url"
                                    )
                                )
                            )
                        )
                    )
                )
            } else {
                NetworkResult.failure(NetworkError.GenericError(RuntimeException("invalid query")))
            }
        }
    }

    private val giphySearch = GiphySearch(port, "secret")

    @Test
    fun `successful response`() {
        val result = giphySearch.search("batman")
        verify(port).get(
            GiphyResponse::class.java,
            "search",
            mapOf(
                "q" to "batman",
                "api_key" to "secret",
                "limit" to "20",
                "offset" to "0"
            )
        )

        assertEquals("response is success", 1, result?.size)
    }

    @Test
    fun `error response`() {
        val result = giphySearch.search("")
        assertEquals("response is error", null, result)
    }
}