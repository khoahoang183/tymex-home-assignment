package com.khoahoang183.basesource.base.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


abstract class BaseBottomSheetFragment(
    @StyleRes private val themeStyle: Int
) : BottomSheetDialogFragment() {

    @LayoutRes
    abstract fun layoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, themeStyle)
        onCreated(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(layoutId(), container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        bindViewEvents(view)
        bindViewModel()
        onPostViewCreated()

    }

    open fun onCreated(savedInstanceState: Bundle?) {}

    open fun setupView(view: View) {}

    open fun onPostViewCreated() {}


    open fun bindViewEvents(view: View) {}

    open fun bindViewModel() {}

    override fun onStart() {
        super.onStart()
        dialog?.let { dialog ->
            val bottomSheet =
                dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as? FrameLayout
            val behavior = bottomSheet?.let { BottomSheetBehavior.from(it) }
            val layoutParams = bottomSheet?.layoutParams as? CoordinatorLayout.LayoutParams
            overrideBottomSheetWindowSize(layoutParams, behavior)
            view?.requestLayout()
        }
    }

    abstract fun overrideBottomSheetWindowSize(
        layoutParams: CoordinatorLayout.LayoutParams?,
        behavior: BottomSheetBehavior<FrameLayout>?
    )

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val d = object : BottomSheetDialog(requireContext(), themeStyle) {
            // implement override here
        }
        d.setOnShowListener {
            val dialogSheet = dialog as? BottomSheetDialog
            val bottomSheet =
                dialogSheet?.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            val bottomSheetBehavior = bottomSheet?.let { BottomSheetBehavior.from(it) }
            bottomSheetBehavior?.addBottomSheetCallback(object :
                BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                }

                override fun onStateChanged(bottomSheet: View, newState: Int) {

                }
            })
        }
        return d
    }

    protected inline infix fun <T> Flow<T>.bindTo(crossinline action: (T) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                collect { action(it) }
            }
        }
    }

    protected inline fun <T> Flow<T>.bindToScope(crossinline action: (T) -> Unit = {}) =
        viewLifecycleOwner.lifecycleScope.launch {
            collect { action(it) }
        }

    protected inline fun launch(
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
        crossinline job: suspend CoroutineScope.() -> Unit
    ) = viewLifecycleOwner.lifecycleScope.launch(coroutineContext) {
        job.invoke(this)
    }

    protected inline fun launchUI(crossinline job: suspend CoroutineScope.() -> Unit) =
        launch(Dispatchers.Main, job)

    protected inline fun launchIO(crossinline job: suspend CoroutineScope.() -> Unit) =
        launch(Dispatchers.IO, job)

    abstract val tagDialog: String

    protected val isViewActive: Boolean
        get() = this.isAdded



//
}