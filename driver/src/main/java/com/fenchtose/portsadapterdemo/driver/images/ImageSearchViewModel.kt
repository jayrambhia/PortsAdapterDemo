package com.fenchtose.portsadapterdemo.driver.images

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.fenchtose.portsadapterdemo.hexagon.images.ImageSearchDriverPort
import com.fenchtose.portsadapterdemo.hexagon.images.ImageSearchState
import com.fenchtose.portsadapterdemo.hexagon.images.SearchResult
import dagger.Module
import dagger.Provides

class ImageSearchViewModel(private val port: ImageSearchDriverPort) : ViewModel() {
    private val state: MutableLiveData<ImageSearchState> = MutableLiveData()
    private val callback: SearchResult = {
        state.value = it
    }

    init {
        port.addCallback(callback)
        port.initialize()
    }

    fun search(query: String) {
        port.search(query)
    }

    fun state(): LiveData<ImageSearchState> = state

    override fun onCleared() {
        port.removeCallback(callback)
    }
}

class ImageSearchViewModelFactory(private val port: ImageSearchDriverPort) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ImageSearchViewModel(port) as T
    }
}

@Module
class ImageSearchViewModelModule(private val activity: AppCompatActivity) {
    @Provides
    fun adapter(driverPort: ImageSearchDriverPort): ImageSearchViewModel {
        return ViewModelProviders.of(activity, ImageSearchViewModelFactory(driverPort))
            .get(ImageSearchViewModel::class.java)
    }
}