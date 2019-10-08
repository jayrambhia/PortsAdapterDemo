package com.fenchtose.portsadapterdemo.hexagon.images

import com.fenchtose.portsadapterdemo.hexagon.utils.TestDispatchersProvider
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class SearchImagesModuleTests {
    private val port = mock<ImageSearchDrivenPort> {
        on { search("batman") } doReturn listOf(
            SearchImage(
                id = "1",
                url = "some_url",
                source = "test",
                isGif = false
            )
        )

        on { search("test") } doReturn null
    }

    private val module = ImageSearchModule(port, TestDispatchersProvider())

    private val listener = mock<SearchResult>()

    @Before
    fun setup() {
        module.addCallback(listener)
    }

    @Test
    fun `successful search`() {
        runBlocking {
            module.search("batman")
        }
        verifyCaptures(
            listener,
            ImageSearchState(emptyList(), "batman", true, false),
            ImageSearchState(listOf(SearchImage("1", "some_url", "test", false)), "batman", false, false)
        )
    }

    @Test
    fun `search error`() {
        module.search("test")
        verifyCaptures(
            listener,
            ImageSearchState(emptyList(), "test", true, false),
            ImageSearchState(emptyList(), "test", false, true)
        )
    }

    @After
    fun tearDown() {
        module.removeCallback(listener)
    }
}