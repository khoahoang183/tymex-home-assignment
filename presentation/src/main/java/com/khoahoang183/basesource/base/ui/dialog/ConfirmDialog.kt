package com.khoahoang183.basesource.base.ui.dialog

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.khoahoang183.basesource.R
import com.khoahoang183.basesource.databinding.DialogConfirmBinding
import com.khoahoang183.basesource.common.extension.bindingIsVisible
import com.khoahoang183.basesource.common.extension.singleClick
import com.khoahoang183.basesource.common.extension.textVisible
import javax.inject.Inject


interface ConfirmDialog : HostDialogDelegate {

    fun showDialog(
        titleRes: Int,
        messageRes: Int,
        leftRes: Int,
        rightRes: Int,
        onLeftButtonClick: (() -> Unit) = {},
        onRightButtonClick: () -> Unit
    )

    fun showDialog(
        title: String?,
        message: String?,
        leftText: String?,
        rightText: String?,
        onLeftButtonClick: (() -> Unit) = {},
        onRightButtonClick: (() -> Unit) = {}
    )
}

class ConfirmDialogImpl @Inject constructor(context: Fragment) :
    HostDialog<DialogConfirmBinding>(context, DialogConfirmBinding::inflate),
    ConfirmDialog {

    override fun setupDialogConfig(alertDialog: AlertDialog) {
        binding.tvTitle.text = null
        binding.tvContent.text = null
        binding.leftButtonTv.text = null
        binding.rightButtonTv.text = null
    }

    override fun bindDialogView() {
        binding.leftButtonTv.singleClick {
            onLeftButtonTvClick?.invoke()
            dismissDialog()
        }
        binding.rightButtonTv.singleClick {
            onRightButtonTvClick?.invoke()
            dismissDialog()
        }
    }

    private var onLeftButtonTvClick: (() -> Unit)? = null
    private var onRightButtonTvClick: (() -> Unit)? = null

    override fun showDialog(
        titleRes: Int,
        messageRes: Int,
        leftRes: Int,
        rightRes: Int,
        onLeftButtonClick: () -> Unit,
        onRightButtonClick: () -> Unit
    ) {
        binding.tvTitle.textVisible(titleRes)
        binding.tvContent.textVisible(messageRes)
        binding.leftButtonTv.textVisible(leftRes)
        binding.rightButtonTv.textVisible(rightRes)


//        register successfully
//                You will receive a notification via email once approval has been granted.

        binding.buttonDividerVw.bindingIsVisible(leftRes > 0 && rightRes > 0)

        this.onRightButtonTvClick = onRightButtonClick
        this.onLeftButtonTvClick = onLeftButtonClick
        super.showDialog()
    }

    override fun showDialog(
        title: String?,
        message: String?,
        leftText: String?,
        rightText: String?,
        onLeftButtonClick: () -> Unit,
        onRightButtonClick: () -> Unit
    ) {
        binding.tvTitle.textVisible(title)
        binding.tvContent.textVisible(message)
        binding.leftButtonTv.textVisible(leftText)
        binding.rightButtonTv.textVisible(rightText)

        binding.buttonDividerVw.bindingIsVisible(!leftText.isNullOrEmpty() && !rightText.isNullOrEmpty())

        this.onRightButtonTvClick = onRightButtonClick
        this.onLeftButtonTvClick = onLeftButtonClick
        super.showDialog()
    }

    override fun resizeWindowSize(alertDialog: AlertDialog) {
        val window = alertDialog.window
        val oWidth = window?.attributes?.width ?: WindowManager.LayoutParams.MATCH_PARENT
        val oHeight = window?.attributes?.height ?: WindowManager.LayoutParams.WRAP_CONTENT
        val wWidth = context.resources.getDimensionPixelSize(R.dimen.long_200dp)
        val size = onResizeWindowSizeChanged(oWidth, oHeight, wWidth, oHeight)
        window?.setLayout(size.width, size.height)
        window?.setGravity(Gravity.CENTER)
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}
