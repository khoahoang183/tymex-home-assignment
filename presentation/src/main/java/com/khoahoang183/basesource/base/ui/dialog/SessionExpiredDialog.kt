package com.khoahoang183.basesource.base.ui.dialog

import android.app.AlertDialog
import androidx.fragment.app.Fragment
import com.khoahoang183.basesource.R
import com.khoahoang183.basesource.common.extension.gone
import com.khoahoang183.basesource.databinding.DialogConfirmBinding
import com.khoahoang183.basesource.common.extension.singleClick
import javax.inject.Inject


interface SessionExpiredDialog: HostDialogDelegate {

    fun showDialog(onResolveClick: () -> Unit)
}

class SessionExpiredDialogImpl @Inject constructor(context: Fragment) :
    HostDialog<DialogConfirmBinding>(context, DialogConfirmBinding::inflate),
    SessionExpiredDialog {

    override fun setupDialogConfig(alertDialog: AlertDialog) {
        binding.tvTitle.text = ""
        binding.tvContent.text = binding.root.context.getString(R.string.common_session_expired)
        binding.leftButtonTv.gone()
        binding.rightButtonTv.text = binding.root.context.getString(android.R.string.ok)
    }

    override fun bindDialogView() {
        binding.rightButtonTv.singleClick {
            onDialogPositiveClick?.invoke()
            dismissDialog()
        }
    }

    private var onDialogPositiveClick: (() -> Unit)? = null

    override fun showDialog(onResolveClick: () -> Unit) {
        this.onDialogPositiveClick = onResolveClick
        super.showDialog()
    }
}
