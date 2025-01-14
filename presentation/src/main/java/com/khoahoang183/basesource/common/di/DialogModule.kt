package com.khoahoang183.basesource.common.di

import com.khoahoang183.basesource.base.ui.dialog.ConfirmDialog
import com.khoahoang183.basesource.base.ui.dialog.ConfirmDialogImpl
import com.khoahoang183.basesource.base.ui.dialog.LoadingDialog
import com.khoahoang183.basesource.base.ui.dialog.LoadingDialogImpl
import com.khoahoang183.basesource.base.ui.dialog.SessionExpiredDialog
import com.khoahoang183.basesource.base.ui.dialog.SessionExpiredDialogImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(ActivityComponent::class, FragmentComponent::class)
abstract class DialogModule {
    @Binds
    abstract fun provideConfirmDialog(context: ConfirmDialogImpl): ConfirmDialog

    @Binds
    abstract fun provideLoadingDialog(context: LoadingDialogImpl): LoadingDialog

    @Binds
    abstract fun provideSessionExpiredDialog(context: SessionExpiredDialogImpl): SessionExpiredDialog

}