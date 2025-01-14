package com.khoahoang183.basesource.common.uicustom

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalLinearItemDecoration(
    private val spacingTop: Int,
    private val spacingLeft: Int,
    private val spacingRight: Int,
    private val spacingBottom: Int,
    private val spacingTopFirstItem: Int = 0,
    private val spacingTopLastItem: Int = 0,
    private val spacingBottomFirstItem: Int = 0,
    private val spacingBottomLastItem: Int = 0,
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position: Int = parent.getChildAdapterPosition(view)
        val isFirst = position == 0
        val isLast = position == state.itemCount - 1

        outRect.left = spacingLeft
        outRect.right = spacingRight

        outRect.top = if (isFirst && spacingTopFirstItem != 0)
            spacingTopFirstItem
        else if (isLast && spacingTopLastItem != 0)
            spacingTopLastItem
        else
            spacingTop

        outRect.bottom = if (isFirst && spacingBottomFirstItem != 0)
            spacingBottomFirstItem
        else if (isLast && spacingBottomLastItem != 0)
            spacingBottomLastItem
        else
            spacingBottom
    }
}
