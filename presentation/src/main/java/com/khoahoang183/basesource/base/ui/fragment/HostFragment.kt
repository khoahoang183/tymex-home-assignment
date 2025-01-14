package com.khoahoang183.basesource.base.ui.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import androidx.core.view.updatePadding
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import com.khoahoang183.basesource.MainActivity
import com.khoahoang183.basesource.MainNavigator
import com.khoahoang183.basesource.MainViewModel
import com.khoahoang183.basesource.R
import com.khoahoang183.basesource.base.function.BaseViewEvent
import com.khoahoang183.basesource.base.function.BaseViewState
import com.khoahoang183.basesource.base.ui.HostViewModel
import com.khoahoang183.basesource.base.ui.dialog.ConfirmDialog
import com.khoahoang183.basesource.base.ui.dialog.LoadingDialog
import com.khoahoang183.basesource.base.ui.dialog.SessionExpiredDialog
import com.khoahoang183.basesource.common.extension.toErrorString
import com.khoahoang183.basesource.common.helper.AppConstants
import com.khoahoang183.basesource.common.helper.Toaster
import com.khoahoang183.data.base.Failure
import com.khoahoang183.data.base.UserFailure
import com.khoahoang183.data.features.auth.AppAuthenticator
import javax.inject.Inject

abstract class HostFragment<VB : ViewBinding> : BaseBindingFragment<VB>() {


    @Inject
    lateinit var authenticator: AppAuthenticator

    @Inject
    lateinit var dialogProgress: LoadingDialog

    @Inject
    lateinit var confirmDialog: ConfirmDialog

    @Inject
    lateinit var expiredDialog: SessionExpiredDialog

    @Inject
    lateinit var toaster: Toaster

    @Inject
    lateinit var navigatorMain: MainNavigator

    @Inject
    lateinit var appAuthenticator: AppAuthenticator


    abstract val viewModel: HostViewModel

    open val activityViewModel: MainViewModel by activityViewModels()

    private var containerView: View? = null


    open fun getContainer(): View? = null

    var onFilePicked: ((Uri) -> Unit)? = null

    var permissionGrantedCallback: (() -> Unit)? = null
    var permissionDeniedCallback: (() -> Unit)? = null
    var registerBackPress: (() -> Unit)? = null

    var permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            permissionGrantedCallback?.invoke()
        } else {
            permissionDeniedCallback?.invoke()
        }
    }


    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    @CallSuper
    override fun bindViewModel() = with(viewModel) {
        loading bindTo ::showOnLoading
        failure bindTo ::handleOnFailure
    }

    abstract fun bindShareFlow(viewEvent: BaseViewEvent)

    abstract fun bindStateFlow(viewState: BaseViewState)

    open fun handleOnUnauthorized() {
        expiredDialog.showDialog { (activity as? MainActivity)?.handleOnUnauthorized() }
    }

    open fun handleOnFailure(failure: Failure) {
        if (failure is UserFailure.SessionExpiredFailure) {
            handleOnUnauthorized()
        } else {
            showPopupError(failure.toErrorString())
        }
    }

    open fun showPopupError(@StringRes messageRes: Int, callback: () -> Unit = {}) {
        showPopupError(getString(messageRes), callback)
    }

    open fun showPopupError(message: CharSequence?, callback: () -> Unit = {}) {
        confirmDialog.showDialog(
            title = null,
            message = message.toString(),
            leftText = null,
            rightText = getString(R.string.ok),
            onLeftButtonClick = { },
            onRightButtonClick = { }
        )
    }

    open fun showPopupSuccess(@StringRes messageRes: Int, callback: () -> Unit = {}) {
        showPopupSuccess(getString(messageRes), callback)
    }

    open fun showPopupSuccess(message: CharSequence?, callback: () -> Unit = {}) {
        toaster.show(message)
    }

    open fun showOnLoading(loadingStatus: String) {
        when (loadingStatus) {
            AppConstants.EnumLoadingStatus.LOADING.value -> {
                dialogProgress.show()

            }

            AppConstants.EnumLoadingStatus.SUCCESS.value -> {
                dialogProgress.success()
            }

            AppConstants.EnumLoadingStatus.DONE.value -> {
                dialogProgress.hide()

            }
        }
    }

    fun setAppBarColor(statusBarColor: Int, navigationBarColor: Int, isLightBar: Boolean?) {
        (this.activity as? MainActivity)?.setAppBarColor(
            statusBarColor,
            navigationBarColor,
            isLightBar
        )
    }

    fun setAppBarLight() {
        (this.activity as? MainActivity)?.setAppBarLight()
    }

    fun setAppBarDark() {
        (this.activity as? MainActivity)?.setAppBarDark()
    }

    fun updateContainerPaddingTop(containerView: View) {
        this.containerView = containerView
        (this.activity as? MainActivity)?.let { mainActivity ->
            containerView.updatePadding(top = mainActivity.topInset)
        }
    }

    fun updateContainerPaddingBottom(containerView: View) {
        this.containerView = containerView
        (this.activity as? MainActivity)?.let { mainActivity ->
            containerView.updatePadding(bottom = mainActivity.botInset)
        }
    }

    //
    open fun updateContainerVerticalPadding() {
        (this.activity as? MainActivity)?.let { mainActivity ->
            getContainer()?.updatePadding(bottom = mainActivity.botInset)
            getContainer()?.updatePadding(top = mainActivity.topInset)
        }
    }

    val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                handleChooseFile()
            }
        }

    val startForUploadImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.data?.let {
                        onFilePicked?.invoke(it)
                    }
                }

                else -> {}
            }
        }

    val requestMediaPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        if (it.values.find { !it } == null) {
            handleChooseFile()
        } else {
            handleRequestPermissionFailed()
        }
    }

    private fun handleChooseFile() {
//        ImagePicker.with(this)
//            .compress(2048)
//            //.maxResultSize(1440, 3040)
//            .galleryOnly()
//            .createIntent { intent ->
//                startForUploadImageResult.launch(intent)
//            }

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.setType("*/*")
        val i = Intent.createChooser(intent, "File")
        startForUploadImageResult.launch(i)
    }


    private fun handleRequestPermissionFailed() {
        confirmDialog.showDialog(
            titleRes = R.string.empty,
            messageRes = R.string.please_allow_gallery,
            leftRes = R.string.cancel,
            rightRes = R.string.go_to_setting,
            onLeftButtonClick = {},
            onRightButtonClick = {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri: Uri = Uri.fromParts("package", activity?.packageName, null)
                intent.data = uri
                launcher.launch(intent)
            }
        )
    }
}