package com.khoahoang183.domain.features.user

import com.khoahoang183.data.base.Resource
import com.khoahoang183.data.features.user.network.GetGithubUserDetailRequest
import com.khoahoang183.data.features.user.network.GetGithubUsersRequest
import com.khoahoang183.data.features.user.repository.UserRepository
import com.khoahoang183.domain.base.BaseUseCase
import com.khoahoang183.model.features.GithubUserModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGithubUserDetailCase  @Inject constructor(
    private val repository: UserRepository
) : BaseUseCase<GithubUserModel, GetGithubUserDetailRequest>() {

    override fun execute(params: GetGithubUserDetailRequest): Flow<Resource<GithubUserModel?>> {
        return repository.getGithubUserDetail(params)
    }
}