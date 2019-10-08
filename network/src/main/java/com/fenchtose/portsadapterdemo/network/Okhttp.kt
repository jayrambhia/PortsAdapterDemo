package com.fenchtose.portsadapterdemo.network

import com.fenchtose.portsadapterdemo.parser.ResultParser
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.lang.RuntimeException
import java.lang.StringBuilder

interface OkhttpProvider {
    fun newClient(): OkHttpClient
}

class BasicOkhttpProvider : OkhttpProvider {
    override fun newClient(): OkHttpClient {
        return OkHttpClient()
    }
}

class OkhttpRequests(
    clientProvider: OkhttpProvider,
    private val parser: ResultParser,
    private val baseUrl: String
) : NetworkPort {

    private val client = clientProvider.newClient()

    override fun <T> get(responseClass: Class<T>, path: String, params: Map<String, String>): NetworkResult<T> {

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

        return executeRequest(responseClass, request)
    }

    private fun <T> executeRequest(responseClass: Class<T>, request: Request): NetworkResult<T> {
        try {
            val response = client.newCall(request).execute()
            val body = response.body()?.string()
            if (response.isSuccessful && body != null) {
                try {
                    val parsed = parser.parse(responseClass, body)
                    if (parsed != null) {
                        return NetworkResult.success(parsed)
                    }
                } catch (e: Exception) {
                    return NetworkResult.failure(NetworkError.JsonError(e))
                }
            }

            return NetworkResult.failure(NetworkError.GenericError(RuntimeException("something went wrong")))

        } catch (e: IOException) {
            e.printStackTrace()
            return NetworkResult.failure(NetworkError.GenericError(e))
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException(e)
        }
    }
}