package com.khoahoang183.basesource.base.ui.activity

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.khoahoang183.data.base.Failure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

abstract class BaseActivity: AppCompatActivity() {

    @LayoutRes
    protected abstract fun layoutId(): Int

    private val viewResumed = AtomicBoolean(false)

    protected val isViewActive: Boolean
        get() = !this.isFinishing

    override fun onCreate(savedInstanceState: Bundle?) {
        onPreCreate()
        super.onCreate(savedInstanceState)
        onPreContentCreate()
        onCreateContentView()
        onPostContentCreate(savedInstanceState)
        bindViewEvents()
        bindViewModel()
    }

    open fun onPreCreate() {}

    open fun onPreContentCreate() {}

    open fun onCreateContentView() {
        if (layoutId() != View.NO_ID) {
            setContentView(layoutId())
        }
    }

    open fun onPostContentCreate(savedInstanceState: Bundle?) {}

    open fun bindViewEvents() {}

    open fun bindViewModel() {}

    protected inline infix fun <T> Flow<T>.bindTo(crossinline action: (T) -> Unit) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                collect { action(it) }
            }
        }
    }


    open fun showMessage(message: String) {}

    open fun showMessage(@StringRes messageRes: Int) {}

    open fun showOnError(error: Any?) {
        when (error) {
            is String -> showMessage(error)
            is Failure -> showOnFailure(error)
            is Throwable -> {

            }
        }
    }

    open fun showOnFailure(failure: Failure?) {}

}