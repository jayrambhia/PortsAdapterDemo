package com.fenchtose.portsadapterdemo.images

import com.fenchtose.portsadapterdemo.base.ApplicationComponent
import com.fenchtose.portsadapterdemo.commons_android.scopes.ActivityScope
import com.fenchtose.portsadapterdemo.driven.images.ImageSearchDrivenComponent
import com.fenchtose.portsadapterdemo.driver.images.ImageSearchViewModelModule
import com.fenchtose.portsadapterdemo.hexagon.images.ImageSearchHexagon
import dagger.Component

@ActivityScope
@Component(
    dependencies = [
        ApplicationComponent::class,
        ImageSearchDrivenComponent::class
    ],
    modules = [
        ImageSearchHexagon::class,
        ImageSearchViewModelModule::class
    ]
)
interface ImageSearchComponent {
    fun inject(activity: ImageSearchActivity)

    @Component.Builder
    interface Builder {
        fun build(): ImageSearchComponent
        fun hexagon(module: ImageSearchHexagon): Builder
        fun driven(component: ImageSearchDrivenComponent): Builder
        fun app(component: ApplicationComponent): Builder
        fun driver(module: ImageSearchViewModelModule): Builder
    }
}