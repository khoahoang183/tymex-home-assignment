package com.khoahoang183.basesource.base.ui

import android.view.View
import androidx.annotation.StyleRes
import androidx.core.view.updatePadding
import androidx.viewbinding.ViewBinding
import com.khoahoang183.basesource.MainActivity
import com.khoahoang183.basesource.base.ui.dialog.BaseBindingBottomSheetFragment

abstract class HostBottomSheetFragment<VB : ViewBinding> constructor(
    @StyleRes themeStyle: Int
) : BaseBindingBottomSheetFragment<VB>(themeStyle) {

    abstract val viewModel: HostViewModel


    fun updateContainerPaddingTop(containerView: View) {
        (this.activity as? MainActivity)?.let { mainActivity ->
            containerView.updatePadding(top = mainActivity.topInset)
        }
    }

    fun updateContainerPaddingBottom(containerView: View) {
        (this.activity as? MainActivity)?.let { mainActivity ->
            containerView.updatePadding(bottom = mainActivity.botInset)
        }
    }
}