package com.khoahoang183.basesource.features.home.tab1

import com.khoahoang183.basesource.base.function.BaseViewEvent
import com.khoahoang183.basesource.base.function.BaseViewState
import com.khoahoang183.basesource.base.ui.HostViewModel
import com.khoahoang183.basesource.common.helper.AppConstants
import com.khoahoang183.data.base.Event
import com.khoahoang183.data.base.Resource
import com.khoahoang183.data.features.user.network.GetGithubUsersRequest
import com.khoahoang183.domain.base.ext.toEvent
import com.khoahoang183.domain.features.user.GetGithubUserCase
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
class HomeTab1ViewModel @Inject constructor(
    private val getGithubUserCase: GetGithubUserCase
) : HostViewModel() {

    sealed class ViewState : BaseViewState {
        data object EmptyState : ViewState()
        data class ListUser(val payload: Event<MutableList<GithubUserModel>>) : ViewState()
    }

    sealed class ViewEvent : BaseViewEvent {
        data object EmptyEvent : ViewEvent()
    }

    class UIModel : BaseUIModel() {
        var perPage: Int = 20
        var since: Int = 0
        var listUser: MutableList<GithubUserModel> = mutableListOf()

        var isCallingAPI: Boolean = false
        var isReached: Boolean = false
    }

    private val _stateFlow = MutableStateFlow<ViewState>(ViewState.EmptyState)
    val stateFlow: Flow<ViewState> = _stateFlow.asStateFlow()

    private val _shareFlow = MutableSharedFlow<ViewEvent>()
    val shareFlow: SharedFlow<ViewEvent> = _shareFlow.asSharedFlow()

    var dataState = UIModel()

    override fun initViewModel() {
        super.initViewModel()

        launch {
            delay(AppConstants.DELAY_EMIT_DURATION)
            getGithubUser()
        }
    }

    private fun getGithubUser() {
        if (dataState.isReached)
            return

        if (dataState.isCallingAPI)
            return

        launch {
            dataState.isCallingAPI = true
            getGithubUserCase.execute(
                GetGithubUsersRequest(dataState.perPage, dataState.since)
            ).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        dataState.isCallingAPI = false
                        dataState.isReached = resource.data.orEmpty().toMutableList().size < dataState.perPage

                        if (dataState.since == 0)
                            dataState.listUser = resource.data.orEmpty().toMutableList()
                        else
                            dataState.listUser.addAll(
                                resource.data.orEmpty().toMutableList()
                            )

                        dataState.listUser.last().id?.let {
                            dataState.since = it
                        }
                        _stateFlow.emit(ViewState.ListUser(dataState.listUser.toEvent()))
                    }

                    is Resource.Error -> {
                        dataState.isCallingAPI = false
                    }

                    else -> {}
                }
            }
        }
    }

    fun loadMore() {
        if (!dataState.isCallingAPI) {
            getGithubUser()
        }
    }

    fun refreshData() {
        dataState.apply {
            since = 0
            listUser= mutableListOf()
            isCallingAPI = false
            isReached = false
        }
        getGithubUser()
    }

}