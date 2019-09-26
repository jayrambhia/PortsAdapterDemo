package com.fenchtose.portsadapterdemo.network

import com.fenchtose.portsadapterdemo.parser.ResultParser
import dagger.Module
import dagger.Provides

interface NetworkPort {
    fun <T> get(responseClass: Class<T>, path: String, params: Map<String, String>): NetworkResult<T>
}

@Module
class NetworkPortModule(private val baseUrl: String) {
    @Provides
    fun adapter(parser: ResultParser): NetworkPort {
        return OkhttpRequests(BasicOkhttpProvider(), parser, baseUrl)
    }
}