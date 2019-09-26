package com.fenchtose.portsadapterdemo.driven.counters

import com.fenchtose.portsadapterdemo.hexagon.counters.CounterDrivenPort
import dagger.Module
import dagger.Provides

@Module
class CounterDrivenModule(private val counterId: String) {
    @Provides
    fun adapter(): CounterDrivenPort {
        return CountersMap.map[counterId]?.invoke() ?: NoOpCounter()
    }
}