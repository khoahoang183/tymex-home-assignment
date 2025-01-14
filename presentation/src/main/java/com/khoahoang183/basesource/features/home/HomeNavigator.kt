package com.khoahoang183.basesource.features.home

import androidx.fragment.app.Fragment
import com.khoahoang183.basesource.base.ui.navigator.HostFragmentNavigator
import com.khoahoang183.basesource.base.ui.navigator.HostFragmentNavigatorImpl
import javax.inject.Inject

interface HomeNavigator : HostFragmentNavigator {
    fun navigateToLogin()
}

class HomeNavigatorImpl @Inject constructor(fragment: Fragment) :
    HostFragmentNavigatorImpl(fragment),
    HomeNavigator {

    override fun navigateToLogin() {
    }
}