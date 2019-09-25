package com.fenchtose.portsadapterdemo.utils

import com.fenchtose.portsadapterdemo.hexagon.utils.CoroutinesContextProvider
import kotlinx.coroutines.Dispatchers

class AppCoroutinesContextProvider : CoroutinesContextProvider {
    override fun main() = Dispatchers.Main
    override fun io() = Dispatchers.IO
}