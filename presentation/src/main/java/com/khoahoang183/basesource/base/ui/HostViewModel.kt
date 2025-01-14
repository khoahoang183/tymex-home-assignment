package com.khoahoang183.basesource.base.ui

import com.khoahoang183.basesource.base.function.BaseViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class HostViewModel @Inject constructor(
) : BaseViewModel() {

    open class ViewState: BaseViewState {

    }

    init {
        initViewModel()
    }

    open fun initViewModel() {}
}