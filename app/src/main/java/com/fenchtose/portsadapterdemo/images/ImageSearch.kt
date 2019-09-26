package com.fenchtose.portsadapterdemo.images

import com.fenchtose.portsadapterdemo.driven.images.ImageSearchDrivenModule
import com.fenchtose.portsadapterdemo.driver.images.ImageSearchViewModel
import com.fenchtose.portsadapterdemo.driver.images.ImageSearchViewModelModule
import com.fenchtose.portsadapterdemo.hexagon.images.ImageSearchHexagon
import com.fenchtose.portsadapterdemo.network.NetworkPortModule
import com.fenchtose.portsadapterdemo.parser.ResultParserModule
import com.fenchtose.portsadapterdemo.utils.AppCoroutinesContextProviderModule
import dagger.Component

@Component(
    modules = [
        ImageSearchHexagon::class,
        ImageSearchDrivenModule::class,
        NetworkPortModule::class,
        ResultParserModule::class,
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
        fun driven(module: ImageSearchDrivenModule): Builder
        fun network(module: NetworkPortModule): Builder
        fun parser(module: ResultParserModule): Builder
        fun driver(module: ImageSearchViewModelModule): Builder
        fun coroutines(module: AppCoroutinesContextProviderModule): Builder
    }
}