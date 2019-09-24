package com.fenchtose.portsadapterdemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fenchtose.portsadapterdemo.counters.CounterListActivity

class DemoListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_list)

        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = DemoListAdapter(this) { item ->
            val clazz = when (item.id) {
                "counters" -> CounterListActivity::class.java
                else -> null
            }

            clazz?.let { openDemoActivity(it, item.name) }
        }

        recyclerView.adapter = adapter
    }

    private fun openDemoActivity(clazz: Class<*>, name: String) {
        startActivity(Intent(this, clazz).apply { putExtra("name", name) })
    }
}

class DemoListAdapter(context: Context, private val openDemo: (DemoListItem) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    private val demos = listOf(
        DemoListItem(
            id = "counters",
            name = "Counters demo"
        ),
        DemoListItem(
            id = "images",
            name = "Images demo"
        )
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DemoItemViewHolder(
            inflater.inflate(
                R.layout.counter_list_item_layout,
                parent,
                false
            ),
            openDemo
        )
    }

    override fun getItemCount() = demos.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder as DemoItemViewHolder) {
            bind(demos[position])
        }
    }
}

class DemoItemViewHolder(
    private val container: View,
    private val openDemo: (DemoListItem) -> Unit
) : RecyclerView.ViewHolder(container) {
    private val name = container.findViewById<TextView>(R.id.counter_name)

    fun bind(item: DemoListItem) {
        name.text = item.name
        container.setOnClickListener {
            openDemo(item)
        }
    }
}

data class DemoListItem(
    val id: String,
    val name: String
)