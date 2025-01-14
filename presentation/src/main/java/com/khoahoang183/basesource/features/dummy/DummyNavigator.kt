package com.khoahoang183.basesource.features.dummy

import androidx.fragment.app.Fragment
import com.khoahoang183.basesource.base.ui.navigator.HostFragmentNavigator
import com.khoahoang183.basesource.base.ui.navigator.HostFragmentNavigatorImpl
import javax.inject.Inject

interface DummyNavigator : HostFragmentNavigator {

}

class DummyNavigatorImpl @Inject constructor(fragment: Fragment) :
    HostFragmentNavigatorImpl(fragment),
    DummyNavigator {

}