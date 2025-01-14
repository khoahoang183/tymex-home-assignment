package com.khoahoang183.basesource.base.ui.navigator

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.khoahoang183.basesource.R
import timber.log.Timber

interface BaseFragmentNavigator : BaseNavigator {
    val navHostFragmentId: Int

    fun findNavController(): NavController?

    fun navigateUp()

    fun pressBack()

    fun navigate(directions: NavDirections, animation: NavigateAnimation = NavigateAnimation.FADE)


    enum class NavigateAnimation{
        NOTHING,
        FADE,
        SLIDE,
        SLIDE_BACK
    }

}

abstract class BaseFragmentNavigatorImpl(
    protected val fragment: Fragment
) : BaseFragmentNavigator {

    protected var navController: NavController? = null

    override fun findNavController(): NavController? {
        return navController ?: try {
            fragment.findNavController().apply {
                navController = this
            }
        } catch (e: IllegalStateException) {
            // Log Crashlytics as non-fatal for monitoring
            Timber.e(e)
            null
        }
    }

    override fun navigateUp() {
        findNavController()?.navigateUp()
    }

    override fun pressBack() {
        fragment.activity?.onBackPressedDispatcher?.onBackPressed()
    }

    protected fun popBackTo(@IdRes destinationId: Int, inclusive: Boolean = false) {
        findNavController()?.popBackStack(destinationId, inclusive)
    }

    override fun navigate(
        directions: NavDirections,
        animation: BaseFragmentNavigator.NavigateAnimation
    ) {

        findNavController()?.navigate(
            directions,
            when (animation) {
                BaseFragmentNavigator.NavigateAnimation.FADE -> {
                    NavOptions
                        .Builder()
                        .setEnterAnim(R.anim.anim_fade_in)
                        .setExitAnim(R.anim.anim_nothing)
                        .setPopEnterAnim(R.anim.anim_nothing)
                        .setPopExitAnim(R.anim.anim_fade_out)
                        .build()
                }

                BaseFragmentNavigator.NavigateAnimation.SLIDE -> {
                    NavOptions
                        .Builder()
                        .setEnterAnim(R.anim.anim_slide_right_to_current)
                        .setExitAnim(R.anim.anim_nothing)
                        .setPopEnterAnim(R.anim.anim_nothing)
                        .setPopExitAnim(R.anim.anim_slide_current_to_right)
                        .build()
                }

                BaseFragmentNavigator.NavigateAnimation.SLIDE_BACK -> {
                    NavOptions
                        .Builder()
                        .setEnterAnim(R.anim.anim_slide_left_to_current)
                        .setExitAnim(R.anim.anim_nothing)
                        .setPopEnterAnim(R.anim.anim_nothing)
                        .setPopExitAnim(R.anim.anim_slide_current_to_left)
                        .build()
                }

                else -> {
                    NavOptions
                        .Builder().build()
                }
            }
        )
    }
}
