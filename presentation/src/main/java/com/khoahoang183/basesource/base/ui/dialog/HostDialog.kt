package com.khoahoang183.basesource.base.ui.dialog

import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.khoahoang183.basesource.R
import com.khoahoang183.basesource.base.function.InflateAlias

abstract class HostDialog<T : ViewBinding>(
    context: Fragment,
    bindingInflater: InflateAlias<T>,
    @StyleRes themeStyle: Int = R.style.customDialogTheme
) : BaseDialog<T>(context, bindingInflater, themeStyle), HostDialogDelegate {

    override fun show() {
        showDialog()
    }

    override fun hide() {
        dismissDialog()
    }

    override fun success() {
        showDialog()
    }
}

interface HostDialogDelegate {
    fun show()
    fun hide()
    fun success()
}