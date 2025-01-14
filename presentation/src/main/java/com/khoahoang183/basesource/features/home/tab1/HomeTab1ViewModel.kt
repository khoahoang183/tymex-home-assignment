package com.khoahoang183.basesource.features.home.tab1

import com.khoahoang183.model.base.BaseUIModel
import com.khoahoang183.basesource.base.function.BaseViewEvent
import com.khoahoang183.basesource.base.function.BaseViewState
import com.khoahoang183.basesource.base.ui.HostViewModel
import com.khoahoang183.basesource.common.helper.AppConstants
import com.khoahoang183.data.base.Resource
import com.khoahoang183.data.features.user.network.GetGithubUsersRequest
import com.khoahoang183.domain.features.user.GetGithubUserCase
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
class HomeTab1ViewModel @Inject constructor(
    private val getGithubUserCase: GetGithubUserCase
) : HostViewModel() {

    sealed class ViewState : BaseViewState {
        data object EmptyState : ViewState()
        data class ListUser(val payload: MutableList<GithubUserModel>) : ViewState()
    }

    sealed class ViewEvent : BaseViewEvent {
        data object EmptyEvent : ViewEvent()
    }

    class UIModel : BaseUIModel() {
        var since: Int = 0
        var perPage: Int = 20
        var listUser: MutableList<GithubUserModel> = mutableListOf()
    }

    private val _stateFlow = MutableStateFlow<ViewState>(ViewState.EmptyState)
    val stateFlow: Flow<ViewState> = _stateFlow.asStateFlow()

    private val _shareFlow = MutableSharedFlow<ViewEvent>()
    val shareFlow: SharedFlow<ViewEvent> = _shareFlow.asSharedFlow()

    private var dataState = UIModel()

    override fun initViewModel() {
        super.initViewModel()

        launch {
            delay(AppConstants.DELAY_EMIT_DURATION)
            getGithubUser()
        }
    }

    private fun getGithubUser() {
        launch {
            getGithubUserCase.execute(
                GetGithubUsersRequest(dataState.perPage, dataState.since)
            ).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        dataState.listUser = resource.data.orEmpty().toMutableList()
                        _stateFlow.emit(ViewState.ListUser(dataState.listUser))
                    }

                    else -> {}
                }
            }
        }
    }
}