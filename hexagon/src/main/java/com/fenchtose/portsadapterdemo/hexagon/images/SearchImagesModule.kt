package com.fenchtose.portsadapterdemo.hexagon.images

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.suspendCoroutine

typealias SearchResult = (SearchImagesState) -> Unit

interface SearchImagesDriverPort {
    fun addCallback(callback: SearchResult)
    fun removeCallback(callback: SearchResult)
    fun search(query: String)
    fun initialize()
}

interface SearchImagesDrivenPort {
    fun search(query: String): List<SearchImage>?
}

class SearchImagesModule(private val port: SearchImagesDrivenPort) : SearchImagesDriverPort {

    private var callbacks: List<SearchResult> = listOf()

    override fun initialize() {
        publish(SearchImagesState())
    }

    override fun search(query: String) {
        publish(
            SearchImagesState(
                images = emptyList(),
                query = query,
                loading = true,
                error = false
            )
        )
        GlobalScope.launch {
            val images = suspendCoroutine<List<SearchImage>?> { port.search(query) }
            publish(
                SearchImagesState(
                    images ?: emptyList(),
                    query = query,
                    loading = false,
                    error = images == null
                )
            )
        }
    }

    private fun publish(result: SearchImagesState) {
        callbacks.forEach {
            it(result)
        }
    }

    override fun addCallback(callback: SearchResult) {
        callbacks = callbacks + callback
    }

    override fun removeCallback(callback: SearchResult) {
        callbacks = callbacks - callback
    }
}