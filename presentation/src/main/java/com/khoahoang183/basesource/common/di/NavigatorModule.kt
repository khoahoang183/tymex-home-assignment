package com.khoahoang183.basesource.common.di

import com.khoahoang183.basesource.MainNavigator
import com.khoahoang183.basesource.MainNavigatorImpl
import com.khoahoang183.basesource.features.detail.DetailGithubUserNavigator
import com.khoahoang183.basesource.features.detail.DetailGithubUserNavigatorImpl
import com.khoahoang183.basesource.features.home.HomeNavigator
import com.khoahoang183.basesource.features.home.HomeNavigatorImpl
import com.khoahoang183.basesource.features.home.tab1.HomeUserTab1Navigator
import com.khoahoang183.basesource.features.home.tab1.HomeUserTab1NavigatorImpl
import com.khoahoang183.basesource.features.home.tab2.HomeUserTab2Navigator
import com.khoahoang183.basesource.features.home.tab2.HomeUserTab2NavigatorImpl
import com.khoahoang183.basesource.features.splash.SplashNavigator
import com.khoahoang183.basesource.features.splash.SplashNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(ActivityComponent::class, FragmentComponent::class)
abstract class NavigatorModule {
    @Binds
    abstract fun mainNavigator(navigator: MainNavigatorImpl): MainNavigator

    @Binds
    abstract fun splashNavigator(navigator: SplashNavigatorImpl): SplashNavigator

    @Binds
    abstract fun homeNavigator(navigator: HomeNavigatorImpl): HomeNavigator

    @Binds
    abstract fun homeTab1Navigator(navigator: HomeUserTab1NavigatorImpl): HomeUserTab1Navigator

    @Binds
    abstract fun homeTab2Navigator(navigator: HomeUserTab2NavigatorImpl): HomeUserTab2Navigator

    @Binds
    abstract fun detailGithubUserNavigator(navigator: DetailGithubUserNavigatorImpl): DetailGithubUserNavigator
}