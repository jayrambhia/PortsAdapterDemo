package com.fenchtose.portsadapterdemo.driven.counters

import com.fenchtose.portsadapterdemo.hexagon.counters.CounterPort

class FibonacciCounter : CounterPort {

    private val items: MutableList<Int> = mutableListOf(0, 1)

    private fun counter() = items[items.size - 1]

    override fun increment(): Int {
        val next = items[items.size - 2] + items[items.size - 1]
        items.add(next)
        return counter()
    }

    override fun reduction(): Int {
        if (items.size <= 2) {
            throw RuntimeException("Reduction not possible")
        }

        items.removeAt(items.size - 1)
        return counter()
    }

    override fun canIncrease() = true

    override fun canReduce() = items.size > 2

    override fun initialize(): Int {
        items.clear()
        items.addAll(arrayOf(0, 1))
        return counter()
    }

}