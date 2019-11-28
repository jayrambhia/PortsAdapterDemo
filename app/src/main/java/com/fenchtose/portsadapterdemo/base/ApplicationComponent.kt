package com.fenchtose.portsadapterdemo.base

import com.fenchtose.portsadapterdemo.PortsAdaptersApp
import com.fenchtose.portsadapterdemo.hexagon.utils.CoroutinesContextProvider
import com.fenchtose.portsadapterdemo.utils.AppCoroutinesContextProviderModule
import dagger.Component
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppCoroutinesContextProviderModule::class
    ]
)
interface ApplicationComponent {

    fun inject(app: PortsAdaptersApp)
    fun coroutines(): CoroutinesContextProvider

    @Component.Builder
    interface Builder {
        fun coroutines(module: AppCoroutinesContextProviderModule): ApplicationComponent.Builder
        fun build(): ApplicationComponent
    }
}