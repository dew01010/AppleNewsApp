package com.dew.newsapplication.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ItemOffsetDecoration(private val spacing: Int, private val displayMode: Int) : RecyclerView.ItemDecoration() {
    companion object {
        val HORIZONTAL = 0
        val VERTICAL = 1
        val GRID = 2
    }

    var mDisplayMode: Int = -1

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        val position = parent.getChildViewHolder(view).adapterPosition// item position
        val itemCount = state.itemCount
        val layoutManager = parent.layoutManager!!
        setSpace(outRect, layoutManager, position, itemCount)
    }

    private fun setSpace(outRect: Rect, layoutManager: RecyclerView.LayoutManager, position: Int, itemCount: Int) {

        if (displayMode == -1) {
            mDisplayMode = resolveMode(layoutManager)
        }

        when (displayMode) {
            HORIZONTAL -> {
                outRect.left = spacing
                outRect.right = if (position == itemCount - 1) spacing else 0
                outRect.top = spacing
                outRect.bottom = spacing
            }

            VERTICAL -> {
                outRect.left = spacing
                outRect.right = spacing
                outRect.top = spacing
                outRect.bottom = if (position == itemCount - 1) spacing else 0

            }
            GRID -> {
                if (layoutManager is GridLayoutManager) {
                    val cols = layoutManager.spanCount
                    val rows = itemCount / cols

                    outRect.left = spacing
                    outRect.right = if (position % cols == cols - 1) spacing else 0
                    outRect.top = spacing
                    outRect.bottom = if (position / cols == rows - 1) spacing else 0
                }
            }
        }


    }

    private fun resolveMode(layoutManager: RecyclerView.LayoutManager): Int {
        return when {
            layoutManager is GridLayoutManager -> GRID
            layoutManager.canScrollHorizontally() -> HORIZONTAL
            else -> VERTICAL
        }
    }

}
