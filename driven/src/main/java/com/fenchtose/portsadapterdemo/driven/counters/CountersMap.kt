package com.fenchtose.portsadapterdemo.driven.counters

import com.fenchtose.portsadapterdemo.commons.counters.FIBONACCI_COUNTER
import com.fenchtose.portsadapterdemo.commons.counters.SIMPLE_COUNTER

object CountersMap {
    val map = mapOf(
        SIMPLE_COUNTER to { SimpleCounter() },
        FIBONACCI_COUNTER to { FibonacciCounter() }
    )
}