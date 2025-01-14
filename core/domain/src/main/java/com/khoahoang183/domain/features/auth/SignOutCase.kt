package com.khoahoang183.domain.features.auth

import com.khoahoang183.data.features.auth.repository.AuthRepository
import com.khoahoang183.domain.base.BaseUseCase
import com.khoahoang183.data.base.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignOutCase @Inject constructor(
    private val repository: AuthRepository
) : BaseUseCase<Any, Any>() {

    override fun execute(params: Any): Flow<Resource<Any?>> {
        return repository.signOut()
    }
}