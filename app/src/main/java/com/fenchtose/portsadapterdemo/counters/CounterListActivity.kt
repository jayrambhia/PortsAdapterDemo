package com.fenchtose.portsadapterdemo.counters

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fenchtose.portsadapterdemo.R
import com.fenchtose.portsadapterdemo.commons.counters.FIBONACCI_COUNTER
import com.fenchtose.portsadapterdemo.commons.counters.SIMPLE_COUNTER

class CounterListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = intent?.getStringExtra("name") ?: "Unknown demo list"

        setContentView(R.layout.activity_counter_list)

        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = CountersListAdapter(this) { counter ->
            CountersActivity.openCounters(
                this,
                counter
            )
        }

        recyclerView.adapter = adapter
    }
}

class CountersListAdapter(context: Context, private val openCounter: (CounterListItem) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    private val counters = listOf(
        CounterListItem(
            id = SIMPLE_COUNTER,
            name = "Simple counter"
        ),
        CounterListItem(
            id = FIBONACCI_COUNTER,
            name = "Fibonacci counter"
        )
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CounterItemViewHolder(
            inflater.inflate(
                R.layout.counter_list_item_layout,
                parent,
                false
            ),
            openCounter
        )
    }

    override fun getItemCount() = counters.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder as CounterItemViewHolder) {
            bind(counters[position])
        }
    }
}

class CounterItemViewHolder(
    private val container: View,
    private val openCounter: (CounterListItem) -> Unit
) : RecyclerView.ViewHolder(container) {
    private val name = container.findViewById<TextView>(R.id.counter_name)

    fun bind(item: CounterListItem) {
        name.text = item.name
        container.setOnClickListener {
            openCounter(item)
        }
    }
}

data class CounterListItem(
    val id: String,
    val name: String
)

