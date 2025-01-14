package com.khoahoang183.basesource.features.splash

import com.khoahoang183.basesource.base.function.BaseViewEvent
import com.khoahoang183.basesource.base.function.BaseViewState
import com.khoahoang183.basesource.base.ui.HostViewModel
import com.khoahoang183.basesource.common.helper.AppConstants
import com.khoahoang183.data.features.auth.AppAuthenticator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authenticator: AppAuthenticator,
) : HostViewModel() {

    sealed class ViewState : BaseViewState {
        data object EmptyState : ViewState()
    }


    sealed class ViewEvent : BaseViewEvent {
        data object NavigateToSignIn : ViewEvent()
        data object NavigateToHome : ViewEvent()
    }

    private val _stateFlow = MutableStateFlow<ViewState>(ViewState.EmptyState)
    val stateFlow: Flow<ViewState> = _stateFlow.asStateFlow()

    private val _shareFlow = MutableSharedFlow<ViewEvent>()
    val shareFlow: SharedFlow<ViewEvent> = _shareFlow.asSharedFlow()

    override fun initViewModel() {
        super.initViewModel()
        checkAuthentication()
    }

    private fun checkAuthentication() {
        launch {
            delay(AppConstants.DIALOG_DUMMY_LOADING_DURATION)

            /*if (authenticator.isLoggedIn())
                _shareFlow.emit(ViewEvent.NavigateToHome)
            else
                _shareFlow.emit(ViewEvent.NavigateToSignIn)*/

            _shareFlow.emit(ViewEvent.NavigateToHome)
        }
    }
}