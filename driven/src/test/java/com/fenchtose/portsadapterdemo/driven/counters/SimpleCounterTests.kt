package com.fenchtose.portsadapterdemo.driven.counters

import org.junit.Assert.assertEquals
import org.junit.Test

class SimpleCounterTests {

    private val counter = SimpleCounter()

    @Test
    fun `initialize counter`() {
        assertEquals("initialize counter", 0, counter.initialize())
        assertEquals("counter can increase", true, counter.canIncrease())
        assertEquals("counter can reduce", true, counter.canReduce())
        counter.increment()
        counter.increment()
        assertEquals("reinitialize counter", 0, counter.initialize())
    }

    @Test
    fun `increment test`() {
        counter.initialize()

        counter.increment()
        counter.increment()

        assertEquals("counter value", 3, counter.increment())
        assertEquals("counter can increase", true, counter.canIncrease())
        assertEquals("counter can reduce", true, counter.canReduce())
    }

    @Test
    fun `reduction test`() {
        counter.initialize()

        counter.reduction()
        counter.increment()

        assertEquals("counter value", -1, counter.reduction())
        assertEquals("counter can increase", true, counter.canIncrease())
        assertEquals("counter can reduce", true, counter.canReduce())
    }
}