package com.fenchtose.portsadapterdemo.hexagon.utils

import kotlin.coroutines.CoroutineContext

interface CoroutinesContextProvider {
    fun main(): CoroutineContext
    fun io(): CoroutineContext
}