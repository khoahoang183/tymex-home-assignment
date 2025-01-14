package com.khoahoang183.model.features

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class GithubUserModel(
    var login: String? = null,
    var id: Int? = null,
    var node_id: String? = null,
    var avatar_url: String? = null,
    var gravatar_id: String? = null,
    var url: String? = null,
    var html_url: String? = null,
    var followers_url: String? = null,
    var following_url: String? = null,
    var gists_url: String? = null,
    var starred_url: String? = null,
    var subscriptions_url: String? = null,
    var organizations_url: String? = null,
    var repos_url: String? = null,
    var events_url: String? = null,
    var received_events_url: String? = null,
    var type: String? = null,
    var user_view_type: String? = null,
    var site_admin: Boolean? = null,
    var name: String? = null,
    var company: String? = null,
    var blog: String? = null,
    var location: String? = null,
    var email: String? = null,
    var hireable: Boolean? = null,
    var bio: String? = null,
    var twitter_username: String? = null,
    var public_repos: Int? = null,
    var public_gists: Int? = null,
    var followers: Int? = null,
    var following: Int? = null,
    var created_at: String? = null,
    var updated_at: String? = null
): Parcelable