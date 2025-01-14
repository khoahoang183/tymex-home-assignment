package com.khoahoang183.basesource.base.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khoahoang183.data.base.Failure
import com.khoahoang183.data.base.Resource
import com.khoahoang183.domain.base.UseCase
import com.khoahoang183.basesource.base.function.LoadingStatus
import com.khoahoang183.basesource.common.helper.AppConstants
import com.khoahoang183.basesource.common.helper.AppConstants.DIALOG_SUCCESS_DURATION
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


open class BaseViewModel(
    private vararg val useCases: UseCase<*, *>?
) : ViewModel() {


    private val _failure: Channel<Failure> = Channel(Channel.BUFFERED)
    val failure: Flow<Failure> = _failure.receiveAsFlow()

    private val _loading: Channel<LoadingStatus> = Channel(Channel.BUFFERED)
    val loading: Flow<LoadingStatus> = _loading.receiveAsFlow()

    protected inline fun launch(
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
        crossinline job: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(coroutineContext) {
        job.invoke(this)
    }

    override fun onCleared() {
        useCases.let { userCases ->
            if (userCases.isNotEmpty()) userCases.forEach { it?.onCleared() }
        }
        super.onCleared()
    }

    protected suspend fun handleFailure(failure: Failure) {
        _loading.send(AppConstants.EnumLoadingStatus.DONE.value)
        _failure.send(failure)
    }

    protected suspend fun showLoading() {
        _loading.send(AppConstants.EnumLoadingStatus.LOADING.value)
    }

    private suspend fun successAndHideLoading() {
        _loading.send(AppConstants.EnumLoadingStatus.SUCCESS.value)
        delay(DIALOG_SUCCESS_DURATION)
        _loading.send(AppConstants.EnumLoadingStatus.DONE.value)
    }

    protected suspend fun hideLoading() {
        _loading.send(AppConstants.EnumLoadingStatus.DONE.value)
    }

    protected fun <Type> Flow<Resource<Type>>.transformUI(hasLoading: Boolean = true) =
        this.onEach { resource ->
            when (resource) {
                is Resource.Loading -> if(hasLoading) showLoading()
                is Resource.Success -> if(hasLoading) hideLoading()
                is Resource.Error -> hideLoading()
                is Resource.SuccessUpload -> if(hasLoading) hideLoading()
                is Resource.Exception -> {
                    handleFailure(resource.failure)
                }
            }
        }
}
