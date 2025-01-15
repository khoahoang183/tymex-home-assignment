package com.khoahoang183.basesource.features.home.tab2

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.khoahoang183.basesource.R
import com.khoahoang183.basesource.base.function.BaseViewEvent
import com.khoahoang183.basesource.base.function.BaseViewState
import com.khoahoang183.basesource.base.function.InflateAlias
import com.khoahoang183.basesource.base.ui.fragment.HostFragment
import com.khoahoang183.basesource.databinding.FragmentHomeTab1Binding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeTab2Fragment : HostFragment<FragmentHomeTab1Binding>() {

    @Inject
    lateinit var navigator: HomeUserTab2Navigator

    override val viewModel: HomeTab2ViewModel by viewModels()

    override val bindingInflater: InflateAlias<FragmentHomeTab1Binding>
        get() = FragmentHomeTab1Binding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAppBarColor(R.color.color_main_3, R.color.color_main, isLightBar = true)
    }

    override fun onResume() {
        super.onResume()
        setAppBarColor(R.color.color_main_3, R.color.color_main, isLightBar = true)
    }

    override fun bindViewModel() {
        super.bindViewModel()
        with(viewModel) {
            stateFlow bindTo ::bindStateFlow
            shareFlow bindTo ::bindShareFlow
        }
    }

    @SuppressLint("SetTextI18n")
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