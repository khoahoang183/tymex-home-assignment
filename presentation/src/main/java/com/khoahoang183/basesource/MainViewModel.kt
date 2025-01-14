package com.khoahoang183.basesource

import com.khoahoang183.basesource.base.function.BaseViewEvent
import com.khoahoang183.basesource.base.function.BaseViewState
import com.khoahoang183.basesource.base.ui.HostViewModel
import com.khoahoang183.data.base.EnumPositionWhenBackToHome
import com.khoahoang183.data.base.Event
import com.khoahoang183.data.features.auth.AppAuthenticator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authenticator: AppAuthenticator,
) : HostViewModel() {

    sealed class ViewState : BaseViewState {
        data object Idle : ViewState()

    }

    sealed class ViewEvent : BaseViewEvent {
        data object ShowLogin : ViewEvent()
    }

    private val _uiState = MutableStateFlow<ViewState>(ViewState.Idle)
    val uiState: Flow<ViewState> = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<ViewEvent>()
    val event: SharedFlow<ViewEvent> = _event.asSharedFlow()

    fun logoutImmediately() {
        launch {
            authenticator.removeUserAuth()
            _event.emit(ViewEvent.ShowLogin)
        }
    }
}