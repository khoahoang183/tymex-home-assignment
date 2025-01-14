package com.khoahoang183.basesource.features.dummy

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.khoahoang183.basesource.databinding.FragmentDummyBinding
import com.khoahoang183.basesource.base.function.BaseViewEvent
import com.khoahoang183.basesource.base.function.BaseViewState
import com.khoahoang183.basesource.base.function.InflateAlias
import com.khoahoang183.basesource.base.ui.fragment.HostFragment
import com.khoahoang183.basesource.common.extension.safeClick
import com.khoahoang183.basesource.features.splash.SplashNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DummyFragment : HostFragment<FragmentDummyBinding>() {

    @Inject
    lateinit var navigator: SplashNavigator

    override val viewModel: DummyViewModel by viewModels()

    override val bindingInflater: InflateAlias<FragmentDummyBinding>
        get() = FragmentDummyBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAppBarLight()
    }

    override fun onResume() {
        super.onResume()
        setAppBarLight()
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

        }
    }

    override fun bindViewEvents() {
        super.bindViewEvents()

        binding.apply {

        }
    }

    override fun bindShareFlow(viewEvent: BaseViewEvent) {
        when (viewEvent ) {
            else -> {

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