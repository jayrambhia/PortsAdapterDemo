package com.fenchtose.portsadapterdemo.utils

import com.fenchtose.portsadapterdemo.hexagon.utils.CoroutinesContextProvider
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

class AppCoroutinesContextProvider : CoroutinesContextProvider {
    override fun main() = Dispatchers.Main
    override fun io() = Dispatchers.IO
}

// TODO Singleton scope
@Module
class AppCoroutinesContextProviderModule {
    @Provides
    fun contextProvider(): CoroutinesContextProvider = AppCoroutinesContextProvider()
}