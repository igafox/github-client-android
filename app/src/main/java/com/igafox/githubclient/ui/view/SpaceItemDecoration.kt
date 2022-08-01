package com.igafox.githubclient.ui.view

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import android.view.View

class SpaceItemDecoration(private val spaceHeight: Int) : androidx.recyclerview.widget.RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: androidx.recyclerview.widget.RecyclerView, state: androidx.recyclerview.widget.RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = spaceHeight
    }
}