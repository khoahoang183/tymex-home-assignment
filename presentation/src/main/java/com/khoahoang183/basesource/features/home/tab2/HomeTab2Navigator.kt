package com.khoahoang183.basesource.features.home.tab2

import androidx.fragment.app.Fragment
import com.khoahoang183.basesource.base.ui.navigator.HostFragmentNavigator
import com.khoahoang183.basesource.base.ui.navigator.HostNestedFragmentNavigatorImpl
import javax.inject.Inject


interface HomeUserTab2Navigator : HostFragmentNavigator {

}

class HomeUserTab2NavigatorImpl @Inject constructor(fragment: Fragment) :
    HostNestedFragmentNavigatorImpl(fragment),
    HomeUserTab2Navigator {

}