package com.fenchtose.portsadapterdemo.images

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fenchtose.portsadapterdemo.R
import com.fenchtose.portsadapterdemo.commons.counters.FLICKR_SEARCH
import com.fenchtose.portsadapterdemo.commons.counters.GIPHY_SEARCH
import com.fenchtose.portsadapterdemo.commons_android.widgets.SimpleAdapter
import com.fenchtose.portsadapterdemo.commons_android.widgets.itemViewBinder

class ImageSearchListActivity : AppCompatActivity() {
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
                SearchListItem(
                    id = FLICKR_SEARCH,
                    name = "Flickr"
                ),
                SearchListItem(
                    id = GIPHY_SEARCH,
                    name = "Giphy"
                )
            )
        )
    }

    private fun openSearchDemo(search: SearchListItem) {
        ImageSearchActivity.openSearch(this, search)
    }

    private fun bindCounter(view: View, search: SearchListItem) {
        view.findViewById<TextView>(R.id.name).text = search.name
        view.setOnClickListener { openSearchDemo(search) }
    }
}

data class SearchListItem(
    val id: String,
    val name: String
)