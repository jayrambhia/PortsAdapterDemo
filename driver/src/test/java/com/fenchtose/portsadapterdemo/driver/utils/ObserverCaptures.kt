package com.fenchtose.portsadapterdemo.driver.utils

import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert


inline fun <reified T : Any> verifyCapture(
    observer: Observer<T>,
    expected: T,
    message: String = ""
) {
    argumentCaptor<T> {
        verify(observer, atLeastOnce()).onChanged(capture())
        Assert.assertEquals(message, expected, lastValue)
    }
}