package com.fenchtose.portsadapterdemo.counters

import com.fenchtose.portsadapterdemo.driven.counters.CounterDrivenModule
import com.fenchtose.portsadapterdemo.driver.counters.CounterViewModelModule
import com.fenchtose.portsadapterdemo.driver.counters.CountersViewModel
import com.fenchtose.portsadapterdemo.hexagon.counters.CounterHexagonModule
import dagger.Component

@Component(
    modules = [
        CounterHexagonModule::class,
        CounterDrivenModule::class,
        CounterViewModelModule::class
    ]
)
interface CounterComponent {
    fun viewModel(): CountersViewModel

    @Component.Builder
    interface Builder {
        fun build(): CounterComponent
        fun driver(module: CounterViewModelModule): Builder
        fun hexagon(module: CounterHexagonModule): Builder
        fun driven(module: CounterDrivenModule): Builder
    }
}