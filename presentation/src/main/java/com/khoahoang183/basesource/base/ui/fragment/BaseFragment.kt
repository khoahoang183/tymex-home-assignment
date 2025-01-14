package com.khoahoang183.basesource.base.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.khoahoang183.basesource.common.extension.safeClick
import com.khoahoang183.basesource.common.helper.InputModeLifecycleHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


abstract class BaseFragment : Fragment() {

    private val viewResumed = AtomicBoolean(false)
    open val enableBackPressedDispatcher: Boolean = false

    protected val isViewActive: Boolean
        get() = this.isAdded && this.isRemoving.not()

    @LayoutRes
    abstract fun layoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewResumed.lazySet(false)
        onCreated(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(layoutId(), container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onPreViewCreated(savedInstanceState)
        bindViewModel()
        setupView(view)
        bindViewEvents(view)
        onPostViewCreated(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        if (viewResumed.getAndSet(true)) { onResumed() }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setupBackPressedDispatcher()
    }

    override fun onDetach() {
        super.onDetach()
    }

    private fun setupBackPressedDispatcher() {
        val callback = object : OnBackPressedCallback(enableBackPressedDispatcher) {
            override fun handleOnBackPressed() {
                if (consumeBackPressed()) {
                    // do stuff
                } else {
                    isEnabled = false
                    activity?.onBackPressedDispatcher?.onBackPressed()
                }
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(this, callback)
    }

    open fun onCreated(savedInstanceState: Bundle?) {}

    open fun onPreViewCreated(savedInstanceState: Bundle?) {}
    open fun onPostViewCreated(savedInstanceState: Bundle?) {}

    open fun onResumed() {}

    open fun setupView(view: View) {}

    open fun bindViewEvents(view: View) {}

    open fun bindViewModel() {}

    open fun consumeBackPressed(): Boolean = false

    protected inline infix fun <T> Flow<T>.bindTo(crossinline action: (T) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                collect {
                    action(it)
                }
            }
        }
    }

    protected inline infix fun <T> Flow<T>.bindToLatest(crossinline action: (T) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                collectLatest {
                    action(it)
                }
            }
        }
    }

    protected inline fun <T> Flow<T>.addToScope(crossinline action: (T) -> Unit = {}) =
        viewLifecycleOwner.lifecycleScope.launch {
            collect { action(it) }
        }

    protected inline infix fun View.bindTo(crossinline action: () -> Unit) {
        this.safeClick { action.invoke() }
    }

    protected inline fun launch(
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
        crossinline job: suspend CoroutineScope.() -> Unit
    ) = viewLifecycleOwner.lifecycleScope.launch(coroutineContext) {
        job.invoke(this)
    }

    protected inline fun launchUI(crossinline job: suspend CoroutineScope.() -> Unit) =
        launch(Dispatchers.Main, job)

    protected inline fun launchIO(crossinline job: suspend CoroutineScope.() -> Unit) =
        launch(Dispatchers.IO, job)

    open fun setupWindowSoftInputMode(mode: InputModeLifecycleHelper.Mode) {
        viewLifecycleOwner.lifecycle.addObserver(
            InputModeLifecycleHelper(
                window = activity?.window,
                mode = InputModeLifecycleHelper.Mode.ADJUST_RESIZE
            )
        )
    }
}