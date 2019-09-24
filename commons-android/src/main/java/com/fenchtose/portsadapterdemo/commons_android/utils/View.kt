package com.fenchtose.portsadapterdemo.commons_android.utils

import android.view.View

fun View.show(status: Boolean) {
    visibility = if (status) View.VISIBLE else View.GONE
}

fun View.visible(status: Boolean) {
    visibility = if (status) View.VISIBLE else View.INVISIBLE
}