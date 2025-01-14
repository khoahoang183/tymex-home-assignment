package com.khoahoang183.domain.features.auth

import com.khoahoang183.data.features.auth.repository.AuthRepository
import com.khoahoang183.domain.base.BaseUseCase
import com.khoahoang183.data.base.Resource
import com.khoahoang183.data.features.auth.network.SignInRequest
import com.khoahoang183.data.features.auth.network.UserSignInModel
import com.khoahoang183.model.features.UserModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInCase @Inject constructor(
    private val repository: AuthRepository
) : BaseUseCase<UserSignInModel, SignInRequest>() {

    override fun execute(params: SignInRequest): Flow<Resource<UserSignInModel?>> {
        return repository.signIn(params = params)
    }
}