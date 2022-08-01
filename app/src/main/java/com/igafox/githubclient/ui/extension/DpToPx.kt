package com.igafox.githubclient.ui.extension

import android.content.Context

fun Int.DpToPx(context: Context): Int {
    val metrics = context.resources.displayMetrics
    return (this * metrics.density).toInt()
}

fun Int.PxToDp(context: Context): Int {
    val metrics = context.resources.displayMetrics
    return (this / metrics.density).toInt()
}