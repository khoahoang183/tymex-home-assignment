package com.khoahoang183.basesource

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowCompat
import com.khoahoang183.basesource.base.ui.activity.HostActivity
import com.khoahoang183.basesource.common.extension.colorCompat
import com.khoahoang183.basesource.databinding.ActivityMainBinding
import com.khoahoang183.data.features.auth.AppAuthenticator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : HostActivity<ActivityMainBinding>() {

    var topInset: Int = 0
    var botInset: Int = 0
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var authenticator: AppAuthenticator

    @Inject
    lateinit var navigator: MainNavigator

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = { inflater -> ActivityMainBinding.inflate(inflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun bindViewModel() = with(viewModel) {
        this.uiState bindTo ::bindViewStateChange
        this.event bindTo ::bindActionStateChange
    }

    private fun bindViewStateChange(state: MainViewModel.ViewState) {
        when (state) {
            else -> {}
        }
    }

    private fun bindActionStateChange(event: MainViewModel.ViewEvent) {
        when (event) {
            is MainViewModel.ViewEvent.ShowLogin ->{
                navigator.navigateToLogin()
            }
            else -> {}
        }
    }

    fun handleOnUnauthorized() {
        viewModel.logoutImmediately()
    }


    fun setAppBarLight() {
        setAppBarColor(R.color.color_white, R.color.color_white, isLightBar = true)
    }

    fun setAppBarDark() {
        setAppBarColor(R.color.color_black, R.color.color_black, isLightBar = false)
    }

    fun setAppBarColor(statusBarColor: Int, navigationBarColor: Int, isLightBar: Boolean? = null) {
        isLightBar?.let {
            WindowCompat.getInsetsController(window, window.decorView).run {
                isAppearanceLightStatusBars = isLightBar
                isAppearanceLightNavigationBars = isLightBar
            }
        }
        setStatusBarColor(statusBarColor)
        setNavigationBarColor(navigationBarColor)
    }

    private fun setNavigationBarColor(color: Int) {
        window.navigationBarColor = colorCompat(color)
    }

    private fun setStatusBarColor(color: Int) {
        window.statusBarColor = colorCompat(color)
    }

    fun Activity.getCurrentLatLong(
        callBackLatLong: ((Double?, Double?) -> Unit)? = null
    ) {
        val resultCoarse = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED

        val resultFine = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED


        if (!resultCoarse || !resultFine) {
            callBackLatLong?.invoke(null, null)
            return
        }
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (location != null) {
            val latitude = location.latitude
            val longitude = location.longitude
            callBackLatLong?.invoke(latitude, longitude)
        }
    }
}