package com.khoahoang183.basesource.base.ui.activity

import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding

abstract class BaseBindingActivity<VB : ViewBinding>: BaseActivity() {

    final override fun layoutId(): Int  = View.NO_ID

    protected abstract val bindingInflater: (LayoutInflater) -> VB

    private var _binding: ViewBinding? = null

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    final override fun onCreateContentView() {
        setContentView(bindingInflater.invoke(layoutInflater).apply { _binding = this }.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}