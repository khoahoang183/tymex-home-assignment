package com.khoahoang183.basesource.features.home.tab1

import com.khoahoang183.basesource.base.function.BaseViewEvent
import com.khoahoang183.basesource.base.function.BaseViewState
import com.khoahoang183.basesource.base.ui.HostViewModel
import com.khoahoang183.basesource.common.helper.AppConstants
import com.khoahoang183.data.base.Event
import com.khoahoang183.data.base.Resource
import com.khoahoang183.data.base.orZero
import com.khoahoang183.data.features.user.network.GetGithubUsersRequest
import com.khoahoang183.domain.base.ext.toEvent
import com.khoahoang183.domain.features.user.GetGithubUserCase
import com.khoahoang183.domain.features.user.GetGithubUserLocalCase
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
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeTab1ViewModel @Inject constructor(
    private val getGithubUserCase: GetGithubUserCase,
    private val getGithubUserLocalCase: GetGithubUserLocalCase
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
        var isReachedAPI: Boolean = false
        var isReachedLocal: Boolean = false
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
            getData()
        }
    }

    /**
     * Fetch users data from API
     */
    private fun getGithubUserAPI() {
        launch {
            if (dataState.isReachedAPI)
                return@launch

            if (dataState.isCallingAPI)
                return@launch

            delay(1000)

            dataState.isCallingAPI = true
            getGithubUserCase.execute(
                GetGithubUsersRequest(dataState.perPage, dataState.since)
            ).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        dataState.isCallingAPI = false
                        dataState.isReachedAPI =
                            resource.data.orEmpty().toMutableList().size < dataState.perPage

                        if (dataState.since == 0)
                            dataState.listUser = resource.data.orEmpty().toMutableList()
                        else
                            dataState.listUser.addAll(
                                resource.data.orEmpty().toMutableList()
                            )

                        if (dataState.listUser.isNotEmpty())
                            dataState.since = dataState.listUser.last().id.orZero()

                        Timber.d("khoahoang183 test - listUser =  ${dataState.listUser.size}")
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

    /**
     * Fetch users data from local device
     */
    private fun getGithubUserLocal() {
        launch {
            if (dataState.isReachedLocal)
                return@launch

            getGithubUserLocalCase.execute(
                GetGithubUsersRequest(dataState.perPage, dataState.since)
            ).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        Timber.d("khoahoang183 test - local size =  ${resource.data.orEmpty().size}")
                        dataState.isReachedLocal = true
                            //resource.data.orEmpty().toMutableList().size < dataState.perPage

                        if (dataState.since == 0)
                            dataState.listUser = resource.data.orEmpty().toMutableList()
                        else
                            dataState.listUser.addAll(
                                resource.data.orEmpty().toMutableList()
                            )

                        Timber.d("khoahoang183 test - listUser =  ${dataState.listUser.size}")

                        if (dataState.listUser.isNotEmpty()){
                            dataState.since = dataState.listUser.last().id.orZero()
                            _stateFlow.emit(ViewState.ListUser(dataState.listUser.toEvent()))
                        }else{
                            getGithubUserAPI()
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    fun getData() {
        if (!dataState.isReachedLocal) {
            Timber.d("khoahoang183 test - Load data - local")
            getGithubUserLocal()
        } else if (!dataState.isCallingAPI) {
            Timber.d("khoahoang183 test - Load data - api")
            getGithubUserAPI()
        }
    }

    /**
     * reinit state and fetch data
     */
    fun refreshData() {
        dataState.apply {
            since = 0
            listUser = mutableListOf()
            isCallingAPI = false
            isReachedAPI = false
            isReachedLocal = false
        }
        getData()
    }
}