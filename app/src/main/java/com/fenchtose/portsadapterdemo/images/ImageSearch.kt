package com.fenchtose.portsadapterdemo.images

import com.fenchtose.portsadapterdemo.driven.images.ImageSearchDrivenComponent
import com.fenchtose.portsadapterdemo.driver.images.ImageSearchViewModel
import com.fenchtose.portsadapterdemo.driver.images.ImageSearchViewModelModule
import com.fenchtose.portsadapterdemo.hexagon.images.ImageSearchHexagon
import com.fenchtose.portsadapterdemo.utils.AppCoroutinesContextProviderModule
import dagger.Component

@Component(
    dependencies = [
        ImageSearchDrivenComponent::class
    ],
    modules = [
        ImageSearchHexagon::class,
        ImageSearchViewModelModule::class,
        AppCoroutinesContextProviderModule::class
    ]
)
interface ImageSearchModule {
    fun viewModel(): ImageSearchViewModel

    @Component.Builder
    interface Builder {
        fun build(): ImageSearchModule
        fun hexagon(module: ImageSearchHexagon): Builder
        fun driven(component: ImageSearchDrivenComponent): Builder
        fun driver(module: ImageSearchViewModelModule): Builder
        fun coroutines(module: AppCoroutinesContextProviderModule): Builder
    }
}