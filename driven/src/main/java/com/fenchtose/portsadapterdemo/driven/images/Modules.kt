package com.fenchtose.portsadapterdemo.driven.images

import com.fenchtose.portsadapterdemo.hexagon.images.ImageSearchDrivenPort
import com.fenchtose.portsadapterdemo.network.NetworkPort
import com.fenchtose.portsadapterdemo.network.NetworkPortModule
import com.fenchtose.portsadapterdemo.parser.ResultParserModule
import dagger.Component
import dagger.Module
import dagger.Provides

@Module
class ImageSearchDrivenModule(private val apiKey: String) {
    @Provides
    fun adapter(port: NetworkPort): ImageSearchDrivenPort {
        return FlickrSearch(port, apiKey)
    }
}

@Component(
    modules = [
        ImageSearchDrivenModule::class,
        NetworkPortModule::class,
        ResultParserModule::class
    ]
)
interface ImageSearchDrivenComponent {
    fun adapter(): ImageSearchDrivenPort

    @Component.Builder
    interface Builder {
        fun build(): ImageSearchDrivenComponent
        fun port(port: ImageSearchDrivenModule): Builder
        fun network(module: NetworkPortModule): Builder
        fun parser(module: ResultParserModule): Builder
    }
}
