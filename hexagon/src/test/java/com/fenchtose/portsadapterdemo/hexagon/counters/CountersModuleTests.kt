package com.fenchtose.portsadapterdemo.hexagon.counters

import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.assertEquals
import org.junit.Test

class CountersModuleTests {
    private val port = mock<CounterDrivenPort> {
        var current = 10
        on { initialize() } doAnswer {
            current = 10
            current
        }

        on { canIncrease() } doAnswer {
            current % 3 != 0 || current == 0
        }
        on { canReduce() } doAnswer {
            current % 4 != 0 || current == 0
        }

        on { increment() } doAnswer {
            current += 10
            current
        }

        on { reduction() } doAnswer {
            current -= 10
            current
        }

    }
    private val module = CountersModule(port)

    @Test
    fun `initialize module`() {
        assertEquals("initialize module", CounterState(10, true, true), module.initialize())
    }

    @Test
    fun `increase counter`() {
        module.initialize()
        assertEquals("counter increased", CounterState(20, true, false), module.increment())
        assertEquals("counter increased", CounterState(30, false, true), module.increment())
    }

    @Test
    fun `reduce counter`() {
        module.initialize()
        assertEquals("counter reduced", CounterState(0, true, true), module.reduction())
    }
}