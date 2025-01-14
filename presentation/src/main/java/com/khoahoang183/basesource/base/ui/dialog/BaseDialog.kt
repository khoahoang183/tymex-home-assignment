package com.khoahoang183.basesource.base.ui.dialog

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Size
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.khoahoang183.basesource.base.function.InflateAlias
import com.khoahoang183.basesource.common.extension.windowWidth
import kotlin.math.roundToInt


abstract class BaseDialog<VB : ViewBinding> constructor(
    val context: Fragment,
    bindingInflater: InflateAlias<VB>,
    @StyleRes private val themeStyle: Int
) : DefaultLifecycleObserver {

    private var alertDialog: AlertDialog? = null
    private var _binding: ViewBinding? = null

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    init {
        _binding = bindingInflater.invoke(context.layoutInflater, null, false)
        val builder = AlertDialog.Builder(context.requireContext(), themeStyle)
        @Suppress("UNCHECKED_CAST")
        builder.setView((_binding as VB).root)

        alertDialog = builder.create()
        alertDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog?.setCancelable(false)
        alertDialog?.let { setupDialogConfig(it) }
        alertDialog?.setOnShowListener {
            onDialogShowing()
        }
    }

    @CallSuper
    open fun onDialogShowing() {
        if (context.view != null)
            context.viewLifecycleOwner.lifecycle.addObserver(this)
        bindDialogView()
    }

    open fun bindDialogView() {}

    open fun setupDialogConfig(alertDialog: AlertDialog) {}

    open fun showDialog() {
        if (!context.isRemoving && context.isAdded) {
            if (alertDialog?.isShowing == false) {
                alertDialog?.run {
                    show()
                    resizeWindowSize(this)
                }
            }
        }
    }

    open fun getAlertDialog(): AlertDialog? {
        return alertDialog
    }

    open fun resizeWindowSize(alertDialog: AlertDialog) {
        val windowWidth = context.requireActivity().windowWidth
        val window = alertDialog.window
        val oWidth = window?.attributes?.width ?: WindowManager.LayoutParams.MATCH_PARENT
        val oHeight = window?.attributes?.height ?: WindowManager.LayoutParams.WRAP_CONTENT
        val wWidth = (windowWidth * windowWidthSizeAspectRatio).roundToInt()
        val size = onResizeWindowSizeChanged(oWidth, oHeight, wWidth, oHeight)
        window?.setLayout(size.width, size.height)
        window?.setGravity(Gravity.CENTER)
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    open fun onResizeWindowSizeChanged(
        oldWidth: Int,
        oldHeight: Int,
        newWidth: Int,
        newHeight: Int
    ): Size {
        return Size(newWidth, newHeight)
    }

    open val windowWidthSizeAspectRatio: Float = 0.85f

    open fun dismissDialog() {
        if (!context.isRemoving && context.isAdded) {
            if (alertDialog?.isShowing == true) {
                alertDialog?.dismiss()
            }
        }
    }

    private fun dismiss() {
        if (alertDialog?.isShowing == true) {
            alertDialog?.dismiss()
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        dismiss()
        super.onDestroy(owner)
    }

    val isViewActive: Boolean
        get() = (alertDialog?.isShowing == true) && (!context.isRemoving && context.isAdded)

    val isHostActive: Boolean
        get() = (!context.isRemoving && context.isAdded)
}