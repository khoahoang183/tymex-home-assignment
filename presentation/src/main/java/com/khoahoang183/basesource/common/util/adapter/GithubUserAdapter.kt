package com.khoahoang183.basesource.common.util.adapter

import android.annotation.SuppressLint
import com.khoahoang183.basesource.base.adapter.ListAdapter
import com.khoahoang183.basesource.common.extension.bindingUrl
import com.khoahoang183.basesource.databinding.ItemGithubUserBinding
import com.khoahoang183.model.features.FileModel
import com.khoahoang183.model.features.GithubUserModel

class GithubUserAdapter : ListAdapter<GithubUserModel, ItemGithubUserBinding>(
    mInflate = ItemGithubUserBinding::inflate,
    itemsSame = { old, new -> old.id == new.id },
    contentsSame = { old, new ->
        old.login == new.login &&
                old.node_id == new.node_id
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