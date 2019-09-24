package com.fenchtose.portsadapterdemo.network

interface ResultParser {
    fun <T> parse(json: String): T?
}