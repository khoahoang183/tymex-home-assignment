package com.khoahoang183.basesource.features.home

import com.khoahoang183.basesource.base.function.BaseViewEvent
import com.khoahoang183.basesource.base.function.BaseViewState
import com.khoahoang183.basesource.base.ui.HostViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class HomeUserViewModel @Inject constructor() : HostViewModel() {

    sealed class ViewState : BaseViewState {
        data object EmptyState : ViewState()
    }

    sealed class ViewEvent : BaseViewEvent {
        data object EmptyEvent : ViewEvent()
        data object NavigateToSignIn : ViewEvent()
    }

    private val _stateFlow = MutableStateFlow<ViewState>(ViewState.EmptyState)
    val stateFlow: Flow<ViewState> = _stateFlow.asStateFlow()

    private val _shareFlow = MutableSharedFlow<ViewEvent>()
    val shareFlow: SharedFlow<ViewEvent> = _shareFlow.asSharedFlow()

    override fun initViewModel() {
        super.initViewModel()
    }
}