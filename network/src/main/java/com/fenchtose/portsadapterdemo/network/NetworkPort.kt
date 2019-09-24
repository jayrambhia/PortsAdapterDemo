package com.fenchtose.portsadapterdemo.network

interface NetworkPort {
    fun <T> get(path: String, params: Map<String, String>): NetworkResult<T>
}