package com.fenchtose.portsadapterdemo.driven.images

import com.fenchtose.portsadapterdemo.hexagon.images.SearchImage
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FlickrResponse(
    @Json(name = "photos")
    val feed: FlickrFeed
)

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

fun FlickrImage.url() = "http://farm$farm.static.flickr.com/$server/${id}_$secret.jpg"

fun FlickrImage.toModel(): SearchImage = SearchImage(
    id = id,
    url = url(),
    source = "Flickr",
    isGif = false
)

fun List<FlickrImage>.flickrToModel(): List<SearchImage> = map { it.toModel() }

@JsonClass(generateAdapter = true)
data class GiphyResponse(
    @Json(name = "data")
    val images: List<GiphyImage>
)

@JsonClass(generateAdapter = true)
data class GiphyImage(
    @Json(name = "id")
    val id: String,
    @Json(name = "slug")
    val slug: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "images")
    val bundle: GiphyImageBundle
)

@JsonClass(generateAdapter = true)
data class GiphyImageBundle(
    @Json(name = "fixed_width")
    val fixedWidth: GiphyImageRaw
)

@JsonClass(generateAdapter = true)
data class GiphyImageRaw(
    @Json(name = "url")
    val url: String
)

fun GiphyImage.toModel(): SearchImage = SearchImage(
    id = id,
    url = bundle.fixedWidth.url,
    source = "Giphy",
    isGif = true
)

fun List<GiphyImage>.giphyToModel(): List<SearchImage> = map { it.toModel() }