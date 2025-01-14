package com.khoahoang183.basesource.common.uicustom

import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import timber.log.Timber

class PaddingViewPager2Transformer(
    private val offsetPx: Int,
    private val pageMarginPx: Int,
    private val marginVerticalPx: Int
) : ViewPager2.PageTransformer {

    private val totalMargin: Int = offsetPx + pageMarginPx
    private val cachedOffsets = mutableMapOf<Float, Float>()

    override fun transformPage(page: View, position: Float) {
        val viewPager = requireViewPager(page)
        val offset = cachedOffsets.getOrPut(position) {
            position * -(2 * offsetPx + pageMarginPx)
        }

        Timber.d("Khoa - offset = $offset")
        if (viewPager.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
            page.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                marginStart = totalMargin
                marginEnd = totalMargin
                topMargin = calculateDynamicMargin(position)
                bottomMargin = calculateDynamicMargin(position)
            }

            page.translationX =
                if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                    -offset
                } else {
                    offset
                }
        } else {
            page.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = calculateDynamicMargin(position)
                bottomMargin = calculateDynamicMargin(position)
            }

            page.translationY = offset
        }
    }

    // Function to calculate margin dynamically based on the scroll position
    private fun calculateDynamicMargin(position: Float): Int {
        val absolutePosition = Math.abs(position) // Get absolute position to handle both directions
        return if (absolutePosition >= 1) {
            marginVerticalPx // Maximum margin if item is far away (position >= 1)
        } else {
            // Gradually decrease the margin as the item approaches the center (position == 0)
            (marginVerticalPx * absolutePosition).toInt()
        }
    }

    private fun requireViewPager(page: View): ViewPager2 {
        val parent = page.parent
        val parentParent = parent.parent
        if (parent is RecyclerView && parentParent is ViewPager2) {
            return parentParent
        }
        throw IllegalStateException(
            "Expected the page view to be managed by a ViewPager2 instance."
        )
    }
}
