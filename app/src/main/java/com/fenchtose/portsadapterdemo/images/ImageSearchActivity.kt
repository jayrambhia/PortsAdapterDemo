package com.fenchtose.portsadapterdemo.images

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fenchtose.portsadapterdemo.BuildConfig
import com.fenchtose.portsadapterdemo.R
import com.fenchtose.portsadapterdemo.commons_android.utils.show
import com.fenchtose.portsadapterdemo.commons_android.widgets.SimpleAdapter
import com.fenchtose.portsadapterdemo.commons_android.widgets.itemViewBinder
import com.fenchtose.portsadapterdemo.driven.images.FlickrSearch
import com.fenchtose.portsadapterdemo.driver.images.ImageSearchViewModel
import com.fenchtose.portsadapterdemo.hexagon.images.ImageSearchDriverPort
import com.fenchtose.portsadapterdemo.hexagon.images.ImageSearchModule
import com.fenchtose.portsadapterdemo.hexagon.images.ImageSearchState
import com.fenchtose.portsadapterdemo.hexagon.images.SearchImage
import com.fenchtose.portsadapterdemo.image_loader.ImageLoaderPort
import com.fenchtose.portsadapterdemo.image_loader.glide.GlideImageLoader
import com.fenchtose.portsadapterdemo.network.BasicOkhttpProvider
import com.fenchtose.portsadapterdemo.network.OkhttpRequests
import com.fenchtose.portsadapterdemo.parser.MoshiParser
import com.fenchtose.portsadapterdemo.utils.AppCoroutinesContextProvider

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

        viewModel = ViewModelProviders.of(
            this,
            ImageSearchViewModelFactory(
                ImageSearchModule(
                    FlickrSearch(
                        OkhttpRequests(
                            BasicOkhttpProvider(),
                            MoshiParser(),
                            "https://api.flickr.com/services/"
                        ),
                        BuildConfig.FLICKR_API_KEY
                    ),
                    AppCoroutinesContextProvider()
                )
            )
        ).get(ImageSearchViewModel::class.java)

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

class ImageSearchViewModelFactory(private val port: ImageSearchDriverPort) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ImageSearchViewModel(port) as T
    }
}
