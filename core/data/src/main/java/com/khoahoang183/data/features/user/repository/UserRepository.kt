package com.khoahoang183.data.features.user.repository

import com.khoahoang183.data.base.Resource
import com.khoahoang183.data.base.network.NetworkRepository
import com.khoahoang183.data.features.auth.AppAuthenticator
import com.khoahoang183.data.features.user.UserApiService
import com.khoahoang183.data.features.user.network.GetGithubUserDetailRequest
import com.khoahoang183.data.features.user.network.GetGithubUsersRequest
import com.khoahoang183.model.features.GithubUserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getGithubUsers(request: GetGithubUsersRequest): Flow<Resource<List<GithubUserModel>?>>

    fun getGithubUserDetail(request: GetGithubUserDetailRequest): Flow<Resource<GithubUserModel?>>
}

class UserRepositoryImpl constructor(
    private val authenticator: AppAuthenticator,
    private val apiService: UserApiService,
) : UserRepository, NetworkRepository() {

    override fun getGithubUsers(
        request: GetGithubUsersRequest
    ): Flow<Resource<List<GithubUserModel>?>> {
        return safeNetworkFlow(
            apiService.getGithubUsers(request.perPage, request.since)
        )
    }

    override fun getGithubUserDetail(request: GetGithubUserDetailRequest): Flow<Resource<GithubUserModel?>> {
        return safeNetworkFlow(
            apiService.getGithubUserDetail(request.userId)
        )
    }
}
