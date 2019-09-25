package com.fenchtose.portsadapterdemo.hexagon.images

import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals

fun verifyCaptures(listener: SearchResult, vararg states: ImageSearchState) {
    argumentCaptor<ImageSearchState> {
        verify(listener, times(states.size)).invoke(capture())
        states.forEachIndexed { index, state ->
            assertEquals("callback invoke no. $index", state, allValues[index])
        }
    }
}