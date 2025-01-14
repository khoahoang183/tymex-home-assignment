package com.khoahoang183.basesource.features.detail

import androidx.lifecycle.SavedStateHandle
import com.khoahoang183.basesource.base.function.BaseViewEvent
import com.khoahoang183.basesource.base.function.BaseViewState
import com.khoahoang183.basesource.base.ui.HostViewModel
import com.khoahoang183.basesource.common.helper.AppConstants
import com.khoahoang183.basesource.features.home.tab1.HomeTab1ViewModel.ViewState
import com.khoahoang183.data.base.Resource
import com.khoahoang183.data.features.user.network.GetGithubUserDetailRequest
import com.khoahoang183.data.features.user.network.GetGithubUsersRequest
import com.khoahoang183.domain.features.user.GetGithubUserCase
import com.khoahoang183.domain.features.user.GetGithubUserDetailCase
import com.khoahoang183.model.base.BaseUIModel
import com.khoahoang183.model.features.GithubUserModel
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
class DetailGithubUserViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getGithubUserDetailCase: GetGithubUserDetailCase
) : HostViewModel() {

    sealed class ViewState : BaseViewState {
        data object EmptyState : ViewState()
        data class UserDetailValue(val payload: GithubUserModel?) : ViewState()
    }

    sealed class ViewEvent : BaseViewEvent {
        data object EmptyEvent : ViewEvent()
    }

    data class UIModel(
        var userModel: GithubUserModel? = null
    ) : BaseUIModel()

    private val _stateFlow = MutableStateFlow<ViewState>(ViewState.EmptyState)
    val stateFlow: Flow<ViewState> = _stateFlow.asStateFlow()

    private val _shareFlow = MutableSharedFlow<ViewEvent>()
    val shareFlow: SharedFlow<ViewEvent> = _shareFlow.asSharedFlow()

    private val dataState = UIModel()

    init {
        launch {
            dataState.userModel = savedStateHandle["userModel"]
            delay(AppConstants.DELAY_EMIT_DURATION)
            getGithubUserDetail()
        }
    }

    override fun initViewModel() {
        super.initViewModel()
    }

    private fun getGithubUserDetail() {
        launch {
            getGithubUserDetailCase.invoke(GetGithubUserDetailRequest(dataState.userModel?.login.orEmpty()))
                .transformUI()
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            dataState.userModel = resource.data
                            _stateFlow.emit(ViewState.UserDetailValue(dataState.userModel))
                        }

                        else -> {}
                    }
                }
        }
    }
}