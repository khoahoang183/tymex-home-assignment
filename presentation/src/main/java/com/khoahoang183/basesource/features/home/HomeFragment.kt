package com.khoahoang183.basesource.features.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.khoahoang183.basesource.R
import com.khoahoang183.basesource.base.function.BaseViewEvent
import com.khoahoang183.basesource.base.function.BaseViewState
import com.khoahoang183.basesource.base.function.InflateAlias
import com.khoahoang183.basesource.base.ui.fragment.HostFragment
import com.khoahoang183.basesource.base.ui.navigator.HostFragmentNavigatorTabCallback
import com.khoahoang183.basesource.common.extension.disableTooltipText
import com.khoahoang183.basesource.common.extension.toErrorString
import com.khoahoang183.basesource.databinding.FragmentHomeBinding
import com.khoahoang183.data.base.EnumPositionWhenBackToHome
import com.khoahoang183.data.base.Failure
import com.khoahoang183.data.base.UserFailure
import com.khoahoang183.data.base.orZero
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : HostFragment<FragmentHomeBinding>(), HostFragmentNavigatorTabCallback {

    @Inject
    lateinit var navigator: HomeNavigator
    override val viewModel: HomeUserViewModel by viewModels()
    private var tabSelected: Int = 0

    override val bindingInflater: InflateAlias<FragmentHomeBinding>
        get() = FragmentHomeBinding::inflate

    private fun findNavHostFragment(navHostFragmentId: Int): NavHostFragment? {
        return childFragmentManager.findFragmentById(navHostFragmentId) as? NavHostFragment
    }

    override val enableBackPressedDispatcher: Boolean
        get() = true

    override fun consumeBackPressed(): Boolean {
        activity?.finish()
        return enableBackPressedDispatcher
    }

    override fun getContainer(): View {
        return binding.root
    }

    override fun onCreated(savedInstanceState: Bundle?) {
        super.onCreated(savedInstanceState)
        tabSelected = arguments?.getInt("tabSelect", 0).orZero()
    }

    override fun setupView() {
        val navHostFragment = findNavHostFragment(R.id.mainFcv)
        val navController = navHostFragment?.navController ?: return

        binding.bottomNavView.apply {
            menu.clear()
            disableTooltipText()
            setupWithNavController(navController)
            inflateMenu(R.menu.nav_menu_home_bottom)
        }

        navController.addOnDestinationChangedListener { navController, destination, _ ->
            when (destination.id) {
                R.id.homeUserTab1Fragment -> {
                }


                R.id.homeUserTab2Fragment -> {
                }

                else -> {}
            }
        }

        if (tabSelected != 0) {
            binding.bottomNavView.selectedItemId = when (tabSelected) {
                1 -> R.id.homeUserTab1Fragment
                2 -> R.id.homeUserTab2Fragment
                else -> R.id.homeUserTab1Fragment
            }
        }
    }

    override fun handleOnFailure(failure: Failure) {
        if (failure is UserFailure.SessionExpiredFailure) {
            handleOnUnauthorized()
        } else {
            showPopupError(failure.toErrorString())
        }
    }

    override fun bindViewEvents() {


    }

    override fun onPostViewCreated(savedInstanceState: Bundle?) {
        super.onPostViewCreated(savedInstanceState)
    }

    override fun bindViewModel(): Unit = with(viewModel) {
        super.bindViewModel()
        this.stateFlow bindTo ::bindStateFlow
        this.shareFlow bindTo ::bindShareFlow
    }

    override fun bindStateFlow(viewState: BaseViewState) {

    }

    override fun bindShareFlow(viewEvent: BaseViewEvent) {

    }

    override fun onSelectTabItemId(itemId: Int) {
        binding.bottomNavView.selectedItemId = itemId
    }
}