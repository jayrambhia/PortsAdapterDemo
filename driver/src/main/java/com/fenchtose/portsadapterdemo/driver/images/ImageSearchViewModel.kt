package com.fenchtose.portsadapterdemo.driver.images

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fenchtose.portsadapterdemo.hexagon.images.ImageSearchDriverPort
import com.fenchtose.portsadapterdemo.hexagon.images.ImageSearchState
import com.fenchtose.portsadapterdemo.hexagon.images.SearchResult

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