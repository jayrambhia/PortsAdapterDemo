package com.fenchtose.portsadapterdemo.parser

import dagger.Module
import dagger.Provides

interface ResultParser {
    fun <T> parse(clazz: Class<T>, json: String): T?
}

@Module
class ResultParserModule {
    @Provides
    fun parser(): ResultParser {
        return MoshiParser()
    }
}