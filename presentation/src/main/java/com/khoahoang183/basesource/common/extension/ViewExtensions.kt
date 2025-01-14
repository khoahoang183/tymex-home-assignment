package com.khoahoang183.basesource.common.extension

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.os.SystemClock
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.CheckedTextView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.CheckResult
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.TooltipCompat
import androidx.core.content.ContextCompat
import androidx.core.view.iterator
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import coil.load
import coil.transform.CircleCropTransformation
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.khoahoang183.basesource.R
import com.khoahoang183.basesource.common.helper.AppConstants
import com.khoahoang183.data.base.EnumAccountLevel
import com.khoahoang183.data.base.EnumDeliveryStatus
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.enabled() {
    isEnabled = true
}

fun View.disabled() {
    isEnabled = false
}

fun View.enabled(enabled: Boolean) {
    isEnabled = enabled
}

fun View.visible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

fun View.viewable(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.textVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}


fun TextView.textVisible(@StringRes textRes: Int) {
    this.textVisible(if (textRes != 0) context.getString(textRes) else null)
}

fun TextView.textVisible(text: String?) {
    this.text = text
    this.visibility = if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
}

fun TextView.textVisible(text: CharSequence?) {
    this.text = text
    this.visibility = if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
}

fun TextView.textVisible(text: CharSequence?, type: TextView.BufferType) {
    this.setText(text, type)
    this.visibility = if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
}

fun TextView.setAccountLevelText(accountLevel: String) {
    this.text = accountLevel
    when (accountLevel) {
        EnumAccountLevel.BASIC.value -> {
            setTextColorExt(R.color.color_account_basic)
        }

        EnumAccountLevel.STANDARD.value -> {
            setTextColorExt(R.color.color_account_standard)
        }

        EnumAccountLevel.PREMIUM.value -> {
            setTextColorExt(R.color.color_account_premium)
        }
    }
}


fun View.takeVisibleIf(flags: Int) {
    if (this.visibility == flags) {
        this.visible()
    }
}

fun CheckedTextView.checked() {
    isChecked = true
}

fun CheckedTextView.unchecked() {
    isChecked = false
}

fun View.margin(
    left: Float? = null,
    top: Float? = null,
    right: Float? = null,
    bottom: Float? = null
) {
    layoutParams = (layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
        left?.run { leftMargin = dpToPx(this) }
        top?.run { topMargin = dpToPx(this) }
        right?.run { rightMargin = dpToPx(this) }
        bottom?.run { bottomMargin = dpToPx(this) }
    }
}

fun View.marginPx(left: Int? = null, top: Int? = null, right: Int? = null, bottom: Int? = null) {
    layoutParams = (layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
        left?.run { leftMargin = this }
        top?.run { topMargin = this }
        right?.run { rightMargin = this }
        bottom?.run { bottomMargin = this }
    }
}

fun View.dpToPx(dp: Float): Int = context.dpToPx(dp)

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(windowToken, 0)
}

fun View.hideKeyboard(activity: Activity?) {
    hideKeyboard()
    activity?.currentFocus?.let {
        if (it is EditText) it.clearFocus()
    }
}

fun EditText.requestShowKeyboard() {
    requestFocus()
    setSelection(length())
    showKeyboard()
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

@SuppressLint("ClickableViewAccessibility")
fun View.setupHideSoftKeyboard(activity: Activity?) {
    // Set up touch listener for non-text box views to hide keyboard.
    if (this !is EditText) {
        this.setOnTouchListener { _/*v*/, event ->
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> hideKeyboard(activity)
            }
            false
        }
    }
    //If a layout container, iterate over children and seed recursion.
    if (this is ViewGroup) {
        for (i in 0 until this.childCount) {
            this.getChildAt(i).setupHideSoftKeyboard(activity)
        }
    }
}

inline fun View.safeCallBack(thresholdMs: Long = 500, crossinline listener: () -> Unit) {
    var lastClickTime: Long = 0
    if (tag.notNull()) {
        lastClickTime = tag.toString().toLongOrNull() ?: 0
    }
    val realTime = SystemClock.elapsedRealtime()
    if (realTime - lastClickTime > thresholdMs) {
        tag = realTime
        listener.invoke()
    }
}


inline fun View.safeClick(crossinline listener: (v: View) -> Unit) =
    singleClick(500, listener)

inline fun View.singleClick(thresholdMs: Long = 300, crossinline listener: (v: View) -> Unit) {
    var lastClickTime: Long = 0
    setOnClickListener {
        val realTime = SystemClock.elapsedRealtime()
        if (realTime - lastClickTime > thresholdMs) {
            lastClickTime = realTime
            listener(it)
        }
    }
}

fun ImageView.loadImageDrawable(context: Context, drawableId: Int) {
    setImageDrawable(ContextCompat.getDrawable(context, drawableId))
}

fun ImageView.setColorFilterExt(colorResId: Int) {
    this.apply {
        setColorFilter(ContextCompat.getColor(context, colorResId))
    }
}

@SuppressLint("DiscouragedApi")
fun ImageView.setImageDrawableString(resIdString: String) {
    this.apply {
        val resId = context.resources.getIdentifier(resIdString, "drawable", context.packageName)
        setImageResource(resId)
    }
}

fun ImageView.bindingUrl(
    url: String?,
    placeholder: Int = R.color.color_white,
    isCircle: Boolean = false
) {
        this.load(url) {
            crossfade(true)
            placeholder(placeholder)
            error(placeholder)
            if (isCircle)
                transformations(CircleCropTransformation())
        }
    }

fun ImageView.bindingUrlWithAuthentication(
    url: String?,
    accessToken: String,
    placeholder: Int = R.color.color_white,
    isCircle: Boolean = false
) {
        this.load(url) {
            timber.log.Timber.tag("okhttp.OkHttpClient").d("Bearer '%s'", accessToken)
            addHeader("Authorization", "Bearer $accessToken")
            crossfade(true)
            placeholder(placeholder)
            error(placeholder)
            if (isCircle)
                transformations(CircleCropTransformation())
        }
    }

@CheckResult
fun RecyclerView.getScrolledUpFlow(): Flow<Boolean> {
    return callbackFlow {
        val listener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                trySend(dy > 0)
            }
        }
        addOnScrollListener(listener)
        awaitClose { removeOnScrollListener(listener) }
    }
}

fun RecyclerView.clearDecorations() {
    if (itemDecorationCount > 0) {
        for (i in itemDecorationCount - 1 downTo 0) {
            removeItemDecorationAt(i)
        }
    }
}

/**
 * return findLastCompletelyVisibleItemPosition, return -1 if failed
 */
fun StaggeredGridLayoutManager.findLastCompletelyVisibleItemPosition(): Int {
    val array = IntArray(this.spanCount)
    return this.findLastCompletelyVisibleItemPositions(array).maxOrNull() ?: -1
}

fun BottomNavigationView.disableTooltipText() {
    val menuIterator = menu.iterator()
    while (menuIterator.hasNext()) {
        val menu = menuIterator.next()
        findViewById<View>(menu.itemId)?.let { view ->
            TooltipCompat.setTooltipText(view, null)
            view.setOnLongClickListener { true }
        }
    }
}

fun View.bindingIsVisible(isShow: Boolean, hiddenValue: Int = View.GONE) {
    visibility = if (isShow)
        View.VISIBLE
    else
        hiddenValue
}

fun EditText.togglePassword(enable: Boolean) {
    this.transformationMethod = when (enable) {
        true -> HideReturnsTransformationMethod.getInstance()
        else -> PasswordTransformationMethod.getInstance()
    }
    if (this.isFocused) {
        this.setSelection(this.length())
    }
}

@CheckResult
fun EditText.textChangesFlow(): Flow<CharSequence?> {
    return callbackFlow {
        val listener = doOnTextChanged { text, _, _, _ -> trySend(text) }
        awaitClose { removeTextChangedListener(listener) }
    }
}

inline fun EditText.safeTextChanged(
    delay: Long = 500L,
    crossinline onTextChange: (CharSequence) -> Unit
) {
    safeTextChanged(delay, doFilterNot = { it == null }, onTextChange = onTextChange)
}

@OptIn(FlowPreview::class)
inline fun EditText.safeTextChanged(
    delay: Long = 500L,
    crossinline doFilterNot: suspend (CharSequence?) -> Boolean,
    crossinline onTextChange: (CharSequence) -> Unit
) {
    textChangesFlow()
        .filterNot { doFilterNot.invoke(it) }
        .debounce(delay)
        .onEach { c ->
            onTextChange.invoke(this.text)
        }.run {
            findViewTreeLifecycleOwner()?.lifecycleScope?.let { lifecycleScope ->
                launchIn(lifecycleScope)
            }
        }
}


fun View.bindingEnableButtonConfirm(isEnable: Boolean) {
    this.enabled(isEnable)
    if (isEnable) {
        this.setBackgroundResource(R.drawable.shape_r16_white)
        this.setBackgroundTintListExt(R.color.color_main)
    } else {
        this.setBackgroundResource(R.drawable.shape_r16_white)
        this.setBackgroundTintListExt(R.color.color_common_stroke)
    }
}

fun View.setBackgroundExt(resId: Int) {
    when (this) {
        is Button -> background = ContextCompat.getDrawable(this.context, resId)
        is TextView -> setBackgroundResource(resId)
        is ViewGroup -> background = ContextCompat.getDrawable(this.context, resId)
        is ImageView -> background = ContextCompat.getDrawable(this.context, resId)
        else -> background = ContextCompat.getDrawable(this.context, resId)
    }
}

fun View.setTextColorExt(color: Int) {
    when (this) {
        is EditText -> setTextColor(ContextCompat.getColor(context, color))
        is TextView -> setTextColor(ContextCompat.getColor(context, color))
        //is Button -> setTextColor(ContextCompat.getColor(context, color))
        else -> return
    }
}

fun View.setBackgroundTintListExt(colorResId: Int) {
    this.apply {
        backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                this.context,
                colorResId
            )
        )
    }
}

fun TextView.setDrawableLeft(@DrawableRes drawableID: Int) {
    val drawable = ContextCompat.getDrawable(
        context,
        drawableID
    )
    setCompoundDrawablesWithIntrinsicBounds(
        drawable, null,
        null, null
    )
}

fun TextView.setDrawableRight(@DrawableRes drawableID: Int) {
    val drawable = ContextCompat.getDrawable(
        context,
        drawableID
    )
    setCompoundDrawablesWithIntrinsicBounds(
        null, null,
        drawable, null
    )
}

fun TextView.setDrawableTop(@DrawableRes drawableID: Int) {
    val drawable = ContextCompat.getDrawable(
        context,
        drawableID
    )
    setCompoundDrawablesWithIntrinsicBounds(
        null, drawable,
        null, null
    )
}

fun TextView.setDrawableBottom(@DrawableRes drawableID: Int) {
    val drawable = ContextCompat.getDrawable(
        context,
        drawableID
    )
    setCompoundDrawablesWithIntrinsicBounds(
        null, null,
        null, drawable
    )
}

fun TextView.clearDrawable() {
    setCompoundDrawablesWithIntrinsicBounds(
        null, null,
        null, null
    )
}
