package com.fenchtose.portsadapterdemo.driver.counters

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.fenchtose.portsadapterdemo.hexagon.counters.CounterState
import com.fenchtose.portsadapterdemo.hexagon.counters.CounterDriverPort
import dagger.Module
import dagger.Provides

class CountersViewModel(private val port: CounterDriverPort) : ViewModel() {
    private val data: MutableLiveData<CounterState> = MutableLiveData()

    init {
        data.value = port.initialize()
    }

    fun increment() {
        data.value = port.increment()
    }

    fun reduction() {
        data.value = port.reduction()
    }

    fun state(): LiveData<CounterState> = data
}

@Module
class CounterViewModelModule(private val activity: AppCompatActivity) {
    @Provides
    fun adapter(driverPort: CounterDriverPort): CountersViewModel {
        return ViewModelProviders.of(activity, CountersViewModelFactory(driverPort))
            .get(CountersViewModel::class.java)
    }
}

@Suppress("UNCHECKED_CAST")
class CountersViewModelFactory(private val port: CounterDriverPort) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CountersViewModel(port) as T
    }
}