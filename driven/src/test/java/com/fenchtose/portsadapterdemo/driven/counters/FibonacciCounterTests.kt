package com.fenchtose.portsadapterdemo.driven.counters

import org.junit.Assert
import org.junit.Test

class FibonacciCounterTests {
    private val counter = FibonacciCounter()

    @Test
    fun `initialize counter`() {
        Assert.assertEquals("initialize counter", 1, counter.initialize())
        Assert.assertEquals("counter can increase", true, counter.canIncrease())
        Assert.assertEquals("counter can not reduce", false, counter.canReduce())
        counter.increment()
        counter.increment()
        Assert.assertEquals("reinitialize counter", 1, counter.initialize())
    }

    @Test
    fun `increment test`() {
        counter.initialize()

        counter.increment()
        counter.increment()

        Assert.assertEquals("counter value", 3, counter.increment())
        Assert.assertEquals("counter can increase", true, counter.canIncrease())
        Assert.assertEquals("counter can reduce", true, counter.canReduce())
    }

    @Test
    fun `reduction test`() {
        counter.initialize()

        counter.increment()
        counter.increment()
        counter.increment()

        Assert.assertEquals("counter value", 2, counter.reduction())
        Assert.assertEquals("counter can increase", true, counter.canIncrease())
        Assert.assertEquals("counter can reduce", true, counter.canReduce())
    }

    @Test(expected = RuntimeException::class)
    fun `no reduction test`() {
        counter.initialize()
        counter.reduction()
    }
}