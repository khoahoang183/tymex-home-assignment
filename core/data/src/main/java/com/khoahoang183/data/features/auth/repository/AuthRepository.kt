package com.khoahoang183.data.features.auth.repository

import com.khoahoang183.data.base.Resource
import com.khoahoang183.data.base.network.NetworkRepository
import com.khoahoang183.data.features.auth.AppAuthenticator
import com.khoahoang183.data.features.auth.AuthApiService
import com.khoahoang183.data.features.auth.network.SignInRequest
import com.khoahoang183.data.features.auth.network.SignUpRequest
import com.khoahoang183.data.features.auth.network.UserSignInModel
import com.khoahoang183.model.features.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

interface AuthRepository {

    fun signIn(params: SignInRequest): Flow<Resource<UserSignInModel?>>

    fun signUp(params: SignUpRequest): Flow<Resource<Any?>>

    fun signOut(): Flow<Resource<Any?>>

    fun getMyProfile(params: Any): Flow<Resource<UserModel?>>

}


class AuthRepositoryImpl constructor(
    private val authenticator: AppAuthenticator,
    private val apiService: AuthApiService,
) : AuthRepository, NetworkRepository() {

    override fun signIn(params: SignInRequest): Flow<Resource<UserSignInModel?>> {
        return safeNetworkFlow(
            apiService.signIn(body = params)
        ).onEach {
            if (it is Resource.Success) {
                //authenticator.saveUserModelAuth(userSignInModel = it.data)
            }
        }
    }

    override fun signOut(): Flow<Resource<Any?>> {
        return safeNetworkFlow(
            apiService.signOut()
        ).onEach { data ->
            if (data is Resource.Success || data is Resource.Error) {
                authenticator.removeUserAuth()
            }
        }
    }

    override fun signUp(params: SignUpRequest): Flow<Resource<Any?>> {
        return safeNetworkFlow(
            apiService.signUp(body = params)
        ).onEach {
            if (it is Resource.Success) {
                //authenticator.saveUserModelAuth(it.data)
            }
        }
    }

    override fun getMyProfile(params: Any): Flow<Resource<UserModel?>> {
        return safeNetworkFlow(
            apiService.getMyProfile()
        ).onEach {
            if (it is Resource.Success) {
                authenticator.saveUserModelAuth(it.data)
            }
        }
    }
}