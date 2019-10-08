package com.fenchtose.portsadapterdemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fenchtose.portsadapterdemo.commons_android.widgets.SimpleAdapter
import com.fenchtose.portsadapterdemo.commons_android.widgets.itemViewBinder
import com.fenchtose.portsadapterdemo.counters.CounterListActivity
import com.fenchtose.portsadapterdemo.images.ImageSearchListActivity

class DemoListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_list)

        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = SimpleAdapter(
            listOf(
                itemViewBinder(R.layout.demo_list_item_layout, ::bindDemoItem)
            )
        )

        recyclerView.adapter = adapter

        adapter.update(
            listOf(
                DemoListItem(
                    id = "counters",
                    name = "Counters demo"
                ),
                DemoListItem(
                    id = "images",
                    name = "Images demo"
                )
            )
        )
    }

    private fun openDemo(item: DemoListItem) {
        val clazz = when (item.id) {
            "counters" -> CounterListActivity::class.java
            "images" -> ImageSearchListActivity::class.java
            else -> null
        }

        clazz?.let { openDemoActivity(it, item.name) }
    }

    private fun openDemoActivity(clazz: Class<*>, name: String) {
        startActivity(Intent(this, clazz).apply { putExtra("name", name) })
    }

    private fun bindDemoItem(view: View, item: DemoListItem) {
        view.findViewById<TextView>(R.id.name).text = item.name
        view.setOnClickListener { openDemo(item) }
    }
}

data class DemoListItem(
    val id: String,
    val name: String
)