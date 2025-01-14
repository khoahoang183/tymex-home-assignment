package com.khoahoang183.basesource.base.ui.navigator

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.khoahoang183.basesource.R
import com.khoahoang183.basesource.common.extension.colorCompat


interface HostNestedFragmentNavigator : BaseNestedFragmentNavigator {

    fun navigateToBrowserInternal(url: String)
}

abstract class HostNestedFragmentNavigatorImpl(fragment: Fragment) :
    HostNestedFragmentNavigator,
    BaseNestedFragmentNavigatorImpl(fragment) {

    override val navHostFragmentId: Int
        get() = R.id.mainFcv

    override fun navigateToBrowserInternal(url: String) {
        val context = fragment.activity ?: return
        try {
            val params = CustomTabColorSchemeParams.Builder()
                .setNavigationBarColor(context.colorCompat(R.color.color_white))
                .setToolbarColor(context.colorCompat(R.color.color_white))
                .setSecondaryToolbarColor(context.colorCompat(R.color.color_white))
                .setNavigationBarDividerColor(context.colorCompat(R.color.color_white))
                .build()
            val builder = CustomTabsIntent.Builder()
                .setDefaultColorSchemeParams(params)
                .setShareState(CustomTabsIntent.SHARE_STATE_OFF)
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(context, Uri.parse(url))
        } catch (ignore: ActivityNotFoundException) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            ContextCompat.startActivity(context, browserIntent, bundleOf())
        }
    }
}

