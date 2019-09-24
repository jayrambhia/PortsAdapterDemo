package com.fenchtose.portsadapterdemo.driver.counters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fenchtose.portsadapterdemo.hexagon.counters.CounterState
import com.fenchtose.portsadapterdemo.hexagon.counters.CounterDriverPort

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