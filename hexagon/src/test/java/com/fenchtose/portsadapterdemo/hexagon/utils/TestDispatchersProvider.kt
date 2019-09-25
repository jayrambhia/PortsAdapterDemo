package com.fenchtose.portsadapterdemo.hexagon.utils

import kotlinx.coroutines.Dispatchers

class TestDispatchersProvider : CoroutinesContextProvider {
    override fun main() = Dispatchers.Unconfined
    override fun io() = Dispatchers.Unconfined
}