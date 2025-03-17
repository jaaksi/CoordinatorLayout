package com.example.coordinatorlayoutdemo.util

import android.content.Context


fun Context.getStatusBarHeight(): Int {
    var statusBarHeight = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        statusBarHeight = resources.getDimensionPixelSize(resourceId)
    } else {
        statusBarHeight = (25 * resources.displayMetrics.density + 0.5f).toInt()
    }
    return statusBarHeight
}