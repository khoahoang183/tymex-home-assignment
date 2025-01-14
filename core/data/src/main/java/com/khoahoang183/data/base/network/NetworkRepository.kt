package com.khoahoang183.data.base.network

import com.khoahoang183.data.base.Either
import com.khoahoang183.data.base.Failure
import com.khoahoang183.data.base.Failure.Companion.toFailure
import com.khoahoang183.data.base.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import timber.log.Timber

abstract class NetworkRepository {

    open fun <K> safeNetworkFlow(call: Call<K>): Flow<Resource<K?>> {
        return flow {
            emit(Resource.Loading)
            kotlinx.coroutines.delay(100)
            when (val rs = execute(call = call)) {
                is Either.Success -> {
                    emit(Resource.Success(rs.rightValue))
                }
                is Either.Error -> {
                    emit(Resource.Error((rs.leftValue as Failure.UserError).errorBody))
                }
                is Either.Exception -> {
                    emit(Resource.Exception(rs.leftValue))
                }
                is Either.SuccessUpload -> {
                    emit(Resource.SuccessUpload)
                }
            }
        }.flowOn(Dispatchers.IO).catch { exception ->
            if (exception is Failure) {
                emit(Resource.Exception(exception))
            } else {
                emit(Resource.Exception(Failure.UnknownFailure(exception)))
            }
        }
    }

    private fun <K> execute(call: Call<K>): Either<Failure, K?> =
        try {
            when (val apiResponse = NetworkResponse.createNetworkResponse(call.execute())) {
                is NetworkResponse.ApiSuccessResponse -> {
//                    if(apiResponse.body?.status == true) {
//                        Either.Success(rightValue = apiResponse.body.data)
//                    } else {
//                        Either.Error(leftValue = Failure.UserError(apiResponse.body.error))
//                    }
                    Either.Success(rightValue = apiResponse.body)
                }

                is NetworkResponse.ApiExceptionResponse -> {
                    Either.Exception(leftValue = apiResponse.toFailure())
                }

                else -> {
                    //Upload File Successfully
                    Either.SuccessUpload
                }
            }
        } catch (throwable: Throwable) {
            Timber.d("Okhttp - NetworkRepository - execute throwable = ${throwable.message}")
            Either.Exception(throwable.toFailure())
        }
}