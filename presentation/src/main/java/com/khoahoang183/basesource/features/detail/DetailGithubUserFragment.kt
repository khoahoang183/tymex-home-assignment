package com.khoahoang183.basesource.features.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.khoahoang183.basesource.R
import com.khoahoang183.basesource.base.function.BaseViewEvent
import com.khoahoang183.basesource.base.function.BaseViewState
import com.khoahoang183.basesource.base.function.InflateAlias
import com.khoahoang183.basesource.base.ui.fragment.HostFragment
import com.khoahoang183.basesource.common.extension.bindingUrl
import com.khoahoang183.basesource.common.extension.safeClick
import com.khoahoang183.basesource.common.extension.visible
import com.khoahoang183.basesource.databinding.FragmentDetailUserBinding
import com.khoahoang183.basesource.databinding.FragmentDummyBinding
import com.khoahoang183.basesource.features.dummy.DummyViewModel
import com.khoahoang183.basesource.features.splash.SplashNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailGithubUserFragment : HostFragment<FragmentDetailUserBinding>() {

    @Inject
    lateinit var navigator: DetailGithubUserNavigator

    override val viewModel: DetailGithubUserViewModel by viewModels()

    private val args: DetailGithubUserFragmentArgs by navArgs()

    override val bindingInflater: InflateAlias<FragmentDetailUserBinding>
        get() = FragmentDetailUserBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAppBarColor(R.color.color_main, R.color.color_white, isLightBar = true)
    }

    override fun onResume() {
        super.onResume()
        setAppBarColor(R.color.color_main, R.color.color_white, isLightBar = true)
    }

    override fun bindViewModel() {
        super.bindViewModel()
        with(viewModel) {
            stateFlow bindTo ::bindStateFlow
            shareFlow bindTo ::bindShareFlow
        }
    }

    override fun setupView() {
        super.setupView()

        binding.apply {
            includeTopBar.apply {
                tvTitle.visible()
                tvTitle.text = getString(R.string.detail_github_user)
            }
        }
    }

    override fun bindViewEvents() {
        super.bindViewEvents()

        binding.apply {
            includeTopBar.apply {
                imgLeft.safeClick {
                    navigator.pressBack()
                }
            }

            includeUser.apply {
                args.userModel?.let { model ->
                    imgAvatar.bindingUrl(model.avatar_url)
                    tvName.text = model.login
                    tvDescription.text = model.html_url
                }
            }
        }
    }

    override fun bindShareFlow(viewEvent: BaseViewEvent) {
        when (viewEvent) {
            else -> {

            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun bindStateFlow(viewState: BaseViewState) {
        when (viewState) {
            is DetailGithubUserViewModel.ViewState.UserDetailValue ->{
                viewState.payload?.let { model ->
                    binding.apply {
                        tvFollowersValue.text = "${model.followers}+"
                        tvFollowingValue.text = "${model.following}+"
                        tvBlogValue.text = "${model.blog}+"
                    }
                }
            }
            else -> {

            }
        }
    }
}