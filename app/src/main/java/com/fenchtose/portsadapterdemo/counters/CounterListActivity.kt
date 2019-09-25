package com.fenchtose.portsadapterdemo.counters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fenchtose.portsadapterdemo.R
import com.fenchtose.portsadapterdemo.commons.counters.FIBONACCI_COUNTER
import com.fenchtose.portsadapterdemo.commons.counters.SIMPLE_COUNTER
import com.fenchtose.portsadapterdemo.commons_android.widgets.SimpleAdapter
import com.fenchtose.portsadapterdemo.commons_android.widgets.itemViewBinder

class CounterListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = intent?.getStringExtra("name") ?: "Unknown demo list"

        setContentView(R.layout.activity_counter_list)

        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = SimpleAdapter(
            listOf(
                itemViewBinder(R.layout.counter_list_item_layout, ::bindCounter)
            )
        )

        recyclerView.adapter = adapter

        adapter.update(
            listOf(
                CounterListItem(
                    id = SIMPLE_COUNTER,
                    name = "Simple counter"
                ),
                CounterListItem(
                    id = FIBONACCI_COUNTER,
                    name = "Fibonacci counter"
                )
            )
        )
    }

    private fun openCounterDemo(counter: CounterListItem) {
        CountersActivity.openCounters(this, counter)
    }

    private fun bindCounter(view: View, counter: CounterListItem) {
        view.findViewById<TextView>(R.id.name).text = counter.name
        view.setOnClickListener { openCounterDemo(counter) }
    }
}

data class CounterListItem(
    val id: String,
    val name: String
)

