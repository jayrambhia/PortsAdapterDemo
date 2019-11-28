package com.fenchtose.portsadapterdemo.images

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fenchtose.portsadapterdemo.BuildConfig
import com.fenchtose.portsadapterdemo.PortsAdaptersApp
import com.fenchtose.portsadapterdemo.R
import com.fenchtose.portsadapterdemo.commons.counters.FLICKR_SEARCH
import com.fenchtose.portsadapterdemo.commons.counters.GIPHY_SEARCH
import com.fenchtose.portsadapterdemo.commons_android.utils.show
import com.fenchtose.portsadapterdemo.commons_android.widgets.SimpleAdapter
import com.fenchtose.portsadapterdemo.commons_android.widgets.itemViewBinder
import com.fenchtose.portsadapterdemo.driven.images.DaggerImageSearchDrivenComponent
import com.fenchtose.portsadapterdemo.driven.images.ImageSearchDrivenModule
import com.fenchtose.portsadapterdemo.driver.images.ImageSearchViewModel
import com.fenchtose.portsadapterdemo.driver.images.ImageSearchViewModelModule
import com.fenchtose.portsadapterdemo.hexagon.images.*
import com.fenchtose.portsadapterdemo.image_loader.ImageLoaderPort
import com.fenchtose.portsadapterdemo.image_loader.glide.GlideImageLoader
import com.fenchtose.portsadapterdemo.network.NetworkPortModule
import com.fenchtose.portsadapterdemo.parser.ResultParserModule
import com.fenchtose.portsadapterdemo.utils.AppCoroutinesContextProviderModule
import javax.inject.Inject

class ImageSearchActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: ImageSearchViewModel
    private lateinit var editText: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SimpleAdapter
    private lateinit var imageLoader: ImageLoaderPort

    companion object {
        fun openSearch(context: Context, item: SearchListItem) {
            val intent = Intent(context, ImageSearchActivity::class.java).apply {
                putExtra("search_id", item.id)
                putExtra("search_name", item.name)
            }

            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_images)

        title = intent?.getStringExtra("search_name") ?: "Unknown search"
        val searchId = intent?.getStringExtra("search_id") ?: ""

        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        adapter = SimpleAdapter(
            listOf(
                itemViewBinder(R.layout.search_image_list_item_layout, ::bindImage)
            )
        )

        recyclerView.adapter = adapter
        editText = findViewById(R.id.search)

        imageLoader = GlideImageLoader()

        val drivenComponent = DaggerImageSearchDrivenComponent.builder()
            .port(ImageSearchDrivenModule(searchId, apiKey(searchId)))
            .network(NetworkPortModule(baseUrl(searchId)))
            .parser(ResultParserModule())
            .build()

        DaggerImageSearchComponent.builder()
            .driver(ImageSearchViewModelModule(this))
            .hexagon(ImageSearchHexagon())
            .driven(drivenComponent)
            .app((application as PortsAdaptersApp).appComponent)
            .build()
            .inject(this)

        viewModel.state().observe(this, Observer(::render))

        findViewById<View>(R.id.search_cta).setOnClickListener {
            val query = editText.text.toString().trim()
            if (query.isNotBlank()) {
                viewModel.search(query)
            }
        }
    }

    private fun render(state: ImageSearchState) {
        findViewById<View>(R.id.progressbar).show(state.loading)
        findViewById<View>(R.id.error).show(state.error)
        findViewById<View>(R.id.search_cta).isEnabled = !state.loading
        adapter.update(state.images)
    }

    private fun bindImage(view: View, image: SearchImage) {
        imageLoader.loadSearchedImage(view.findViewById(R.id.image), image.url, image.isGif)
    }

    private fun apiKey(id: String): String {
        return when (id) {
            FLICKR_SEARCH -> BuildConfig.FLICKR_API_KEY
            GIPHY_SEARCH -> BuildConfig.GIPHY_API_KEY
            else -> ""
        }
    }

    private fun baseUrl(id: String): String {
        return when (id) {
            FLICKR_SEARCH -> "https://api.flickr.com/services/"
            GIPHY_SEARCH -> "https://api.giphy.com/v1/gifs/"
            else -> ""
        }
    }
}
