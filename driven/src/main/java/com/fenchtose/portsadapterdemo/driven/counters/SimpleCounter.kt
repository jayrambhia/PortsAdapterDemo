package com.fenchtose.portsadapterdemo.driven.counters

import com.fenchtose.portsadapterdemo.hexagon.counters.CounterPort

class SimpleCounter : CounterPort {

    private var current = 0

    override fun initialize(): Int {
        current = 0
        return current
    }

    override fun increment(): Int {
        current++
        return current
    }

    override fun reduction(): Int {
        current--
        return current
    }

    override fun canIncrease() = true
    override fun canReduce() = true
}