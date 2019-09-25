package com.fenchtose.portsadapterdemo.network

interface NetworkPort {
    fun <T> get(responseClass: Class<T>, path: String, params: Map<String, String>): NetworkResult<T>
}