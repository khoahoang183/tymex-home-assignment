package com.khoahoang183.basesource.features.detail

import androidx.fragment.app.Fragment
import com.khoahoang183.basesource.base.ui.navigator.HostFragmentNavigator
import com.khoahoang183.basesource.base.ui.navigator.HostFragmentNavigatorImpl
import com.khoahoang183.basesource.features.splash.SplashFragmentDirections
import com.khoahoang183.basesource.features.splash.SplashNavigator
import javax.inject.Inject

interface DetailGithubUserNavigator: HostFragmentNavigator {

}

class DetailGithubUserNavigatorImpl @Inject constructor(fragment: Fragment) :
    HostFragmentNavigatorImpl(fragment),
    DetailGithubUserNavigator {

}