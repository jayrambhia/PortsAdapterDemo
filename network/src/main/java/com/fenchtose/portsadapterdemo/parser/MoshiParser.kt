package com.fenchtose.portsadapterdemo.parser

import com.squareup.moshi.Moshi

class MoshiParser : ResultParser {

    private val moshi = Moshi.Builder().build()

    override fun <T> parse(clazz: Class<T>, json: String): T? {
        return moshi.adapter<T>(clazz).fromJson(json)
    }
}