package com.fenchtose.portsadapterdemo.hexagon.counters

interface CounterDriverPort {
    fun increment(): CounterState
    fun reduction(): CounterState

    fun initialize(): CounterState
}

data class CounterState(
    val number: Int,
    val canIncrease: Boolean,
    val canReduce: Boolean
)

class CountersModule(private val counterPort: CounterPort) : CounterDriverPort {
    override fun increment(): CounterState {
        return current(counterPort.increment())
    }

    override fun reduction(): CounterState {
        return current(counterPort.reduction())
    }

    override fun initialize(): CounterState {
        return current(counterPort.initialize())
    }

    private fun current(number: Int) = CounterState(
        number = number,
        canIncrease = counterPort.canIncrease(),
        canReduce = counterPort.canReduce()
    )
}

interface CounterPort {
    fun initialize(): Int

    fun increment(): Int
    fun reduction(): Int

    fun canReduce(): Boolean
    fun canIncrease(): Boolean
}