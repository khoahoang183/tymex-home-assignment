package com.khoahoang183.basesource.base.ui.activity

import androidx.viewbinding.ViewBinding
import com.khoahoang183.basesource.common.helper.Toaster
import javax.inject.Inject

abstract class HostActivity<VB : ViewBinding>: BaseBindingActivity<VB>() {

    @Inject
    lateinit var toaster: Toaster
}