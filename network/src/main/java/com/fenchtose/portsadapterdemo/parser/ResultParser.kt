package com.fenchtose.portsadapterdemo.parser

interface ResultParser {
    fun <T> parse(clazz: Class<T>, json: String): T?
}