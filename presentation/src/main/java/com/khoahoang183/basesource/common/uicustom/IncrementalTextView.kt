package com.khoahoang183.basesource.common.uicustom

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class IncrementalTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {


    private var targetNumber: Int = 0
    private var currentNumber: Int = 0
    private var intervalMillis: Long = 5 // Interval between each increment (in milliseconds)
    private val handler = Handler(Looper.getMainLooper())
    private var isAnimating: Boolean = false

    fun setTargetNumber(number: Int, totalIntervalMillis: Long = 500) {
        this.targetNumber = number
        this.intervalMillis = totalIntervalMillis / targetNumber

        if (!isAnimating) {
            currentNumber = 0
            animateText()
        }
    }

    private fun animateText() {
        isAnimating = true
        if (currentNumber <= targetNumber) {
            text = currentNumber.toString().padStart(targetNumber.toString().length, '0')
            currentNumber++
            handler.postDelayed({ animateText() }, intervalMillis)
        } else {
            // If we've reached the target number, keep updating the text with the target number
            text = currentNumber.toString().padStart(targetNumber.toString().length, '0')
            isAnimating = false
        }
    }
}