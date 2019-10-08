package com.fenchtose.portsadapterdemo.driven.images

import com.fenchtose.portsadapterdemo.hexagon.images.ImageSearchDrivenPort
import com.fenchtose.portsadapterdemo.hexagon.images.SearchImage

class StubSearch : ImageSearchDrivenPort {
    override fun search(query: String): List<SearchImage>? {
        throw RuntimeException("Stub")
    }
}