package com.khoahoang183.basesource.features.home.tab1

import androidx.fragment.app.Fragment
import com.khoahoang183.basesource.base.ui.navigator.BaseFragmentNavigator
import com.khoahoang183.basesource.base.ui.navigator.HostFragmentNavigator
import com.khoahoang183.basesource.base.ui.navigator.HostNestedFragmentNavigatorImpl
import com.khoahoang183.basesource.features.home.HomeFragmentDirections
import com.khoahoang183.model.features.GithubUserModel
import javax.inject.Inject


interface HomeUserTab1Navigator : HostFragmentNavigator {
    fun navigateHomeToDetail(userModel: GithubUserModel)
}

class HomeUserTab1NavigatorImpl @Inject constructor(fragment: Fragment) :
    HostNestedFragmentNavigatorImpl(fragment),
    HomeUserTab1Navigator {

    override fun navigateHomeToDetail(userModel: GithubUserModel) {
        navigate(
            HomeFragmentDirections.actionHomeToDetailUser(userModel),
            BaseFragmentNavigator.NavigateAnimation.SLIDE
        )
    }
}