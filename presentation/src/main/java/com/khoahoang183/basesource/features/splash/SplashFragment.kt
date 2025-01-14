package com.khoahoang183.basesource.features.splash

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.khoahoang183.basesource.R
import com.khoahoang183.basesource.base.function.BaseViewEvent
import com.khoahoang183.basesource.base.function.BaseViewState
import com.khoahoang183.basesource.base.function.InflateAlias
import com.khoahoang183.basesource.base.ui.fragment.HostFragment
import com.khoahoang183.basesource.common.extension.setBackgroundExt
import com.khoahoang183.basesource.common.extension.setColorFilterExt
import com.khoahoang183.basesource.common.helper.AppConstants
import com.khoahoang183.basesource.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : HostFragment<FragmentSplashBinding>() {

    @Inject
    lateinit var navigator: SplashNavigator

    override val viewModel: SplashViewModel by viewModels()

    override val bindingInflater: InflateAlias<FragmentSplashBinding>
        get() = FragmentSplashBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAppBarColor(R.color.color_white, R.color.color_white, isLightBar = true)
    }

    override fun onResume() {
        super.onResume()
        setAppBarColor(R.color.color_white, R.color.color_white, isLightBar = true)
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
    }

    override fun bindViewEvents() {
        super.bindViewEvents()

        binding.apply {
        }
    }

    override fun bindShareFlow(viewEvent: BaseViewEvent) {
        when (viewEvent) {
            is SplashViewModel.ViewEvent.NavigateToHome -> {
                navigator.navigateSplashToHome()
            }

            is SplashViewModel.ViewEvent.NavigateToSignIn -> {
                navigator.navigateSplashToSignin()
            }
        }
    }

    override fun bindStateFlow(viewState: BaseViewState) {
        when (viewState) {
            else -> {

            }
        }
    }


}