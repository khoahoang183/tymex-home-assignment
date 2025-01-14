package com.khoahoang183.basesource.base.ui.dialog

import android.util.Size
import androidx.fragment.app.Fragment
import com.khoahoang183.basesource.R
import com.khoahoang183.basesource.databinding.DialogProgressBinding
import com.khoahoang183.basesource.common.extension.dpToPx
import javax.inject.Inject


interface LoadingDialog : HostDialogDelegate

class LoadingDialogImpl @Inject constructor(context: Fragment) :
    HostDialog<DialogProgressBinding>(
        context,
        DialogProgressBinding::inflate,
        R.style.loadingDialogTheme
    ),
    LoadingDialog {

    override fun onResizeWindowSizeChanged(
        oldWidth: Int,
        oldHeight: Int,
        newWidth: Int,
        newHeight: Int
    ): Size {
        return Size(context.dpToPx(200f), context.dpToPx(200f))
    }

    init {
        timber.log.Timber.d("LoadingDialog init")
    }

    override fun show() {
        if (binding.animationView.tag != R.raw.raw_loading) {
            binding.animationView.tag = R.raw.raw_loading
            binding.animationView.setAnimation(R.raw.raw_loading)
        }
        binding.contentTv.text = context.getString(R.string.common_dialog_loading)
        showDialog()
    }

    override fun success() {
        if (binding.animationView.tag != R.raw.raw_success) {
            binding.animationView.tag = R.raw.raw_success
            binding.animationView.setAnimation(R.raw.raw_success)
        }
        binding.contentTv.text = context.getString(R.string.common_dialog_Success)
        showDialog()
    }

    override fun hide() {
        dismissDialog()
    }
}