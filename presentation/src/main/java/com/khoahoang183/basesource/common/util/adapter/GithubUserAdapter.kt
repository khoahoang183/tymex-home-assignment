package com.khoahoang183.basesource.common.util.adapter

import android.annotation.SuppressLint
import com.khoahoang183.basesource.base.adapter.ListAdapter
import com.khoahoang183.basesource.common.extension.bindingUrl
import com.khoahoang183.basesource.databinding.ItemGithubUserBinding
import com.khoahoang183.model.features.GithubUserModel

class GithubUserAdapter : ListAdapter<GithubUserModel, ItemGithubUserBinding>(
    mInflate = ItemGithubUserBinding::inflate,
    itemsSame = { old, new -> old.id == new.id && old.login == new.login },
    contentsSame = { old, new ->
        old.avatar_url == new.avatar_url &&
        old.url == new.url &&
        old.html_url == new.html_url
    }
) {

    @SuppressLint("SetTextI18n")
    override fun bindViewHolder(
        binding: ItemGithubUserBinding,
        model: GithubUserModel,
        position: Int,
        viewType: Int
    ) {
        binding.apply {
            imgAvatar.bindingUrl(model.avatar_url)
            tvName.text = model.login
            tvDescription.text = model.html_url
        }
    }
}