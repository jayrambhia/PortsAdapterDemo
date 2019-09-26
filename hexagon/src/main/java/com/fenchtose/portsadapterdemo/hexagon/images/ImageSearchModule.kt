package com.fenchtose.portsadapterdemo.hexagon.images

import com.fenchtose.portsadapterdemo.hexagon.utils.CoroutinesContextProvider
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.*

typealias SearchResult = (ImageSearchState) -> Unit

interface ImageSearchDriverPort {
    fun addCallback(callback: SearchResult)
    fun removeCallback(callback: SearchResult)
    fun search(query: String)
    fun initialize()
}

interface ImageSearchDrivenPort {
    fun search(query: String): List<SearchImage>?
}

@Module
class ImageSearchHexagon {
    @Provides
    fun hexagon(
        driven: ImageSearchDrivenPort,
        contextProvider: CoroutinesContextProvider
    ): ImageSearchDriverPort {
        return ImageSearchModule(driven, contextProvider)
    }
}

class ImageSearchModule internal constructor(
    private val port: ImageSearchDrivenPort,
    private val contextProvider: CoroutinesContextProvider
) : ImageSearchDriverPort {

    private var callbacks: List<SearchResult> = listOf()

    override fun initialize() {
        publish(ImageSearchState())
    }

    override fun search(query: String) {
        publish(
            ImageSearchState(
                images = emptyList(),
                query = query,
                loading = true,
                error = false
            )
        )

        GlobalScope.launch(contextProvider.main()) {
            val images = withContext(contextProvider.io()) {
                port.search(query)
            }

            publish(
                ImageSearchState(
                    images ?: emptyList(),
                    query = query,
                    loading = false,
                    error = images == null
                )
            )
        }
    }

    private fun publish(result: ImageSearchState) {
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