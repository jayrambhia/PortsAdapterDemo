package com.fenchtose.portsadapterdemo.driver.counters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.fenchtose.portsadapterdemo.driver.utils.verifyCapture
import com.fenchtose.portsadapterdemo.hexagon.counters.CounterDriverPort
import com.fenchtose.portsadapterdemo.hexagon.counters.CounterState
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CountersViewModelTests {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: CountersViewModel
    private val observer = mock<Observer<CounterState>>()

    private val driverPort = mock<CounterDriverPort> {
        on { initialize() } doReturn CounterState(0, true, true)
        on { increment() } doReturn CounterState(10, false, true)
        on { reduction() } doReturn CounterState(-10, true, false)
    }

    @Before
    fun setup() {
        viewModel = CountersViewModel(driverPort)
        viewModel.state().observeForever(observer)
    }

    @Test
    fun `check initial state`() {
        verifyCapture(observer, CounterState(0, true, true), message = "initial counter state")
    }

    @Test
    fun `increment counter`() {
        viewModel.increment()
        verifyCapture(observer, CounterState(10, false, true), message =  "counter incremented once")
    }

    @Test
    fun `reduce counter`() {
        viewModel.reduction()
        verifyCapture(observer, CounterState(-10, true, false), message =  "counter reduced once")
    }

}