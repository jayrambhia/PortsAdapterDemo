package com.fenchtose.portsadapterdemo.images

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fenchtose.portsadapterdemo.BuildConfig
import com.fenchtose.portsadapterdemo.R
import com.fenchtose.portsadapterdemo.commons_android.utils.show
import com.fenchtose.portsadapterdemo.commons_android.widgets.SimpleAdapter
import com.fenchtose.portsadapterdemo.commons_android.widgets.itemViewBinder
import com.fenchtose.portsadapterdemo.driven.images.ImageSearchDrivenModule
import com.fenchtose.portsadapterdemo.driver.images.ImageSearchViewModel
import com.fenchtose.portsadapterdemo.driver.images.ImageSearchViewModelModule
import com.fenchtose.portsadapterdemo.hexagon.images.*
import com.fenchtose.portsadapterdemo.image_loader.ImageLoaderPort
import com.fenchtose.portsadapterdemo.image_loader.glide.GlideImageLoader
import com.fenchtose.portsadapterdemo.network.NetworkPortModule
import com.fenchtose.portsadapterdemo.parser.ResultParserModule
import com.fenchtose.portsadapterdemo.utils.AppCoroutinesContextProviderModule

class ImageSearchActivity : AppCompatActivity() {

    private lateinit var viewModel: ImageSearchViewModel
    private lateinit var editText: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SimpleAdapter
    private lateinit var imageLoader: ImageLoaderPort

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_images)

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

        viewModel = DaggerImageSearchModule.builder()
            .driver(ImageSearchViewModelModule(this))
            .hexagon(ImageSearchHexagon())
            .coroutines(AppCoroutinesContextProviderModule())
            .driven(ImageSearchDrivenModule(BuildConfig.FLICKR_API_KEY))
            .network(NetworkPortModule("https://api.flickr.com/services/"))
            .parser(ResultParserModule())
            .build()
            .viewModel()

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
        imageLoader.loadSearchedImage(view.findViewById(R.id.image), image.url)
    }
}
