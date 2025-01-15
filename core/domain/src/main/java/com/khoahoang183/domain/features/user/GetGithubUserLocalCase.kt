package com.khoahoang183.domain.features.user

import com.khoahoang183.data.base.Resource
import com.khoahoang183.data.features.user.network.GetGithubUsersRequest
import com.khoahoang183.data.features.user.repository.UserRepository
import com.khoahoang183.domain.base.BaseUseCase
import com.khoahoang183.model.features.GithubUserModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGithubUserLocalCase @Inject constructor(
    private val repository: UserRepository
) : BaseUseCase<List<GithubUserModel>, GetGithubUsersRequest>() {

    override fun execute(params: GetGithubUsersRequest): Flow<Resource<List<GithubUserModel>?>> {
        return repository.getGithubUsersLocal(params)
    }
}