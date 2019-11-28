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

@Module
class AppCoroutinesContextProviderModule {
    @Singleton
    @Provides
    fun contextProvider(): CoroutinesContextProvider = AppCoroutinesContextProvider()
}