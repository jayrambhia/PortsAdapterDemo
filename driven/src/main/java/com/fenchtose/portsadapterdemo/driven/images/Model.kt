package com.fenchtose.portsadapterdemo.driven.images

import com.fenchtose.portsadapterdemo.hexagon.images.SearchImage
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FlickrFeed(
    @Json(name = "photo")
    val images: List<FlickrImage>
)

@JsonClass(generateAdapter = true)
data class FlickrImage(
    @Json(name = "id")
    val id: String,
    @Json(name = "farm")
    val farm: String,
    @Json(name = "server")
    val server: String,
    @Json(name = "secret")
    val secret: String
)

fun FlickrImage.url() = "http://farm$farm.static.flickr.com/$server/{$id}_$secret.jpg"

fun FlickrImage.toModel(): SearchImage = SearchImage(
    id = id,
    url = url(),
    source = "Flickr"
)

fun List<FlickrImage>.toModel(): List<SearchImage> = map { it.toModel() }