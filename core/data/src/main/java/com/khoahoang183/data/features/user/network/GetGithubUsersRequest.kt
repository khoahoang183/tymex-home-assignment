package com.khoahoang183.data.features.user.network

data class GetGithubUsersRequest(
    var perPage:Int,
    var since:Int
)