package com.khoahoang183.domain.base

import com.khoahoang183.data.base.Resource
import kotlinx.coroutines.flow.Flow

abstract class BaseUseCase<out Type, in Params> where Type : Any {

    abstract fun execute(params: Params): Flow<Resource<Type?>>

    operator fun invoke(params: Params): Flow<Resource<Type?>> = execute(params)

    open fun onCleared() {}

    class None
}