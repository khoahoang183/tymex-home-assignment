package com.khoahoang183.basesource.base.ui.navigator

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import timber.log.Timber


interface BaseNestedFragmentNavigator : BaseFragmentNavigator

abstract class BaseNestedFragmentNavigatorImpl(fragment: Fragment) :
    BaseFragmentNavigatorImpl(fragment.requireParentFragment()) {

    override fun findNavController(): NavController? {
        return navController ?: try {
            fragment.parentFragment?.findNavController().apply {
                navController = this
            }
        } catch (e: IllegalStateException) {
            // Log Crashlytics as non-fatal for monitoring
            Timber.e(e)
            null
        }
    }
}
