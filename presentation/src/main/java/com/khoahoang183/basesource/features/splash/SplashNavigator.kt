package com.khoahoang183.basesource.features.splash

import androidx.fragment.app.Fragment
import com.khoahoang183.basesource.base.ui.navigator.BaseFragmentNavigator
import com.khoahoang183.basesource.base.ui.navigator.HostFragmentNavigator
import com.khoahoang183.basesource.base.ui.navigator.HostFragmentNavigatorImpl
import javax.inject.Inject

interface SplashNavigator : HostFragmentNavigator {

    fun navigateSplashToSignin()

    fun navigateSplashToHome()

}

class SplashNavigatorImpl @Inject constructor(fragment: Fragment) :
    HostFragmentNavigatorImpl(fragment),
    SplashNavigator {
    override fun navigateSplashToSignin() {

    }

    override fun navigateSplashToHome() {
        navigate(SplashFragmentDirections.actionSigninToHome())
    }

}