package com.khoahoang183.basesource.base.ui.navigator

import androidx.annotation.IdRes

interface HostFragmentNavigatorTabCallback {

    fun onSelectTabItemId(@IdRes itemId: Int)
}

interface NestedFragmentNavigatorTabCallback {

    fun onTabItemReselected()
}