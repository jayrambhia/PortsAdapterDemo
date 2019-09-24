package com.fenchtose.portsadapterdemo.network

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.lang.RuntimeException
import java.lang.StringBuilder

class OkhttpRequests(
    private val client: OkHttpClient,
    private val parser: ResultParser,
    private val baseUrl: String
) : NetworkPort {

    override fun <T> get(path: String, params: Map<String, String>): NetworkResult<T> {

        val urlBuilder = StringBuilder(baseUrl).append(path)
        var joiner = "?"
        params.forEach {
            urlBuilder.append("$joiner${it.key}=${it.value}")
            joiner = "&"
        }

        val request = Request.Builder()
            .url("$urlBuilder")
            .get()
            .build()

        return executeRequest(request)
    }

    private fun <T> executeRequest(request: Request): NetworkResult<T> {
        try {
            val response = client.newCall(request).execute()
            val body = response.body()?.string()
            if (response.isSuccessful && body != null) {
                try {
                    val parsed = parser.parse<T>(body)
                    if (parsed != null) {
                        return NetworkResult.success(parsed)
                    }
                } catch (e: Exception) {
                    return NetworkResult.failure(NetworkError.JsonError(e))
                }
            }

            return NetworkResult.failure(NetworkError.GenericError(RuntimeException("something went wrong")))

        } catch (e: IOException) {
            return NetworkResult.failure(NetworkError.GenericError(e))
        }
    }
}