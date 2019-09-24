package com.fenchtose.portsadapterdemo.network

class NetworkResult<T> private constructor(private val result: Any?) {
    val isFailure: Boolean get() = result is Failure
    val isSuccess: Boolean get() = result !is Failure

    companion object {
        fun <T> success(value: T): NetworkResult<T> = NetworkResult(value)
        fun <T> failure(exception: NetworkError): NetworkResult<T> =
            NetworkResult<T>(Failure(exception))
    }

    @PublishedApi
    internal val exception: NetworkError
        get() = (result as Failure).exception

    @PublishedApi
    internal val value: T
        get() = result as T

    private class Failure(@JvmField val exception: NetworkError)
}

fun <T> NetworkResult<T>.optSuccess(): T? {
    return if (isSuccess) value else null
}

sealed class NetworkError(message: String, cause: Throwable? = null) : Throwable(message, cause) {
    class GenericError(error: Exception) : NetworkError("generic error", error)
    class JsonError(error: Exception) : NetworkError("json error", error)
}