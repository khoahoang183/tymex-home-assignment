package com.khoahoang183.basesource

import androidx.hilt.work.HiltWorkerFactory
import androidx.multidex.MultiDexApplication
import androidx.work.Configuration
import com.khoahoang183.basesource.common.helper.AppConstants
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class Application: MultiDexApplication(), androidx.work.Configuration.Provider {

    override fun onCreate() {
        super.onCreate()

        settingTimber()
    }

    private fun settingTimber() {
        if(AppConstants.isDebugMode){
            Timber.plant(Timber.DebugTree())
        }
    }

    @Inject
    lateinit var hiltWorkerFactory: HiltWorkerFactory
    override val workManagerConfiguration: Configuration
        get() = androidx.work.Configuration.Builder()
            .setWorkerFactory(hiltWorkerFactory)
            .build()


}