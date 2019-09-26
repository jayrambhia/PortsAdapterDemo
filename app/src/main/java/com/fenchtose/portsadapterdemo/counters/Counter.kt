package com.fenchtose.portsadapterdemo.counters

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.fenchtose.portsadapterdemo.driven.counters.CountersMap
import com.fenchtose.portsadapterdemo.driven.counters.NoOpCounter
import com.fenchtose.portsadapterdemo.driver.counters.CountersViewModel
import com.fenchtose.portsadapterdemo.hexagon.counters.CounterDriverPort
import com.fenchtose.portsadapterdemo.hexagon.counters.CounterDrivenPort
import com.fenchtose.portsadapterdemo.hexagon.counters.CountersModule
import dagger.Component
import dagger.Module
import dagger.Provides

@Module
class CounterDriverPortModule {
    @Provides
    fun counterDriverPort(drivenPort: CounterDrivenPort): CounterDriverPort {
        return CountersModule(drivenPort)
    }
}

@Module
class CounterDrivenPortModule(private val counterId: String) {
    @Provides
    fun counterDrivenPort(): CounterDrivenPort {
        return CountersMap.map[counterId]?.invoke() ?: NoOpCounter()
    }
}

@Module
class CounterViewModelModule(private val activity: AppCompatActivity) {
    @Provides
    fun viewModel(driverPort: CounterDriverPort): CountersViewModel {
        return ViewModelProviders.of(activity, CountersViewModelFactory(driverPort))
            .get(CountersViewModel::class.java)
    }
}

@Component(
    modules = [
        CounterDriverPortModule::class,
        CounterDrivenPortModule::class,
        CounterViewModelModule::class
    ]
)
interface CounterComponent {
    fun viewModel(): CountersViewModel
}

@Suppress("UNCHECKED_CAST")
class CountersViewModelFactory(private val port: CounterDriverPort) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CountersViewModel(port) as T
    }
}