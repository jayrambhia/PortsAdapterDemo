package com.fenchtose.portsadapterdemo.hexagon.images

data class SearchImage(
    val id: String,
    val url: String,
    val source: String
)

data class ImageSearchState(
    val images: List<SearchImage> = emptyList(),
    val query: String = "",
    val loading: Boolean = false,
    val error: Boolean = false
)