package com.fenchtose.portsadapterdemo.driver.images

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fenchtose.portsadapterdemo.hexagon.images.SearchImagesDriverPort
import com.fenchtose.portsadapterdemo.hexagon.images.SearchImagesState
import com.fenchtose.portsadapterdemo.hexagon.images.SearchResult

class ImageSearchViewModel(private val port: SearchImagesDriverPort) : ViewModel() {
    private val state: MutableLiveData<SearchImagesState> = MutableLiveData()
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

    fun state(): LiveData<SearchImagesState> = state

    override fun onCleared() {
        port.removeCallback(callback)
    }
}