package com.fenchtose.portsadapterdemo.driven.counters

import com.fenchtose.portsadapterdemo.hexagon.counters.CounterPort

class NoOpCounter : CounterPort {
    override fun initialize(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun increment(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun reduction(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun canReduce(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun canIncrease(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}