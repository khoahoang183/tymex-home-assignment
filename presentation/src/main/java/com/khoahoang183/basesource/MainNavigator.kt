package com.khoahoang183.basesource

import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.khoahoang183.basesource.base.ui.navigator.HostNavigator
import javax.inject.Inject

abstract class MainNavigator : HostNavigator() {
    abstract fun navigateToLogin()
}

class MainNavigatorImpl @Inject constructor(
    private val activity: FragmentActivity
) : MainNavigator() {
    private fun findNavController(): NavController? {
        return activity.supportFragmentManager
            .findFragmentById(R.id.fcvHostNavHostFragment)
            ?.findNavController()
    }

    override fun navigateToLogin() {
        //findNavController()?.navigate(R.id.action_main_to_login)
    }
}