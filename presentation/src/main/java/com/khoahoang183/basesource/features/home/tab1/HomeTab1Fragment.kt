package com.khoahoang183.basesource.features.home.tab1

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khoahoang183.basesource.R
import com.khoahoang183.basesource.base.function.BaseViewEvent
import com.khoahoang183.basesource.base.function.BaseViewState
import com.khoahoang183.basesource.base.function.InflateAlias
import com.khoahoang183.basesource.base.ui.fragment.HostFragment
import com.khoahoang183.basesource.common.extension.dpToPx
import com.khoahoang183.basesource.common.extension.safeCallBack
import com.khoahoang183.basesource.common.uicustom.VerticalLinearItemDecoration
import com.khoahoang183.basesource.common.util.adapter.GithubUserAdapter
import com.khoahoang183.basesource.databinding.FragmentHomeTab1Binding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class HomeTab1Fragment : HostFragment<FragmentHomeTab1Binding>() {

    @Inject
    lateinit var navigator: HomeUserTab1Navigator

    override val viewModel: HomeTab1ViewModel by viewModels()

    private val adapterUsers by lazy {
        GithubUserAdapter()
    }

    override val bindingInflater: InflateAlias<FragmentHomeTab1Binding>
        get() = FragmentHomeTab1Binding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAppBarColor(R.color.color_main_3, R.color.color_main, isLightBar = true)
    }

    override fun onResume() {
        super.onResume()
        setAppBarColor(R.color.color_main_3, R.color.color_main, isLightBar = true)
    }

    override fun bindViewModel() {
        super.bindViewModel()
        with(viewModel) {
            stateFlow bindTo ::bindStateFlow
            shareFlow bindTo ::bindShareFlow
        }
    }

    @SuppressLint("SetTextI18n")
    override fun setupView() {
        super.setupView()
        binding.apply {
            recyData.apply {
                itemAnimator = null
                addItemDecoration(
                    VerticalLinearItemDecoration(
                        spacingTopFirstItem = dpToPx(16f),
                        spacingTop = 0,
                        spacingLeft = dpToPx(16f),
                        spacingBottom = dpToPx(16f),
                        spacingRight = dpToPx(16f),
                        spacingBottomLastItem = 0
                    )
                )
                adapter = adapterUsers
            }
        }
    }

    override fun bindViewEvents() {
        super.bindViewEvents()
        binding.apply {

            adapterUsers.onItemClick { item ->
                navigator.navigateHomeToDetail(item)
            }

            swipeRefresh.setOnRefreshListener {
                swipeRefresh.isRefreshing = true
                viewModel.refreshData()
                swipeRefresh.isRefreshing = false
            }

            recyData.setOnScrollChangeListener { view, _, _, _, _ ->
                try {
                    val lastVisibleItem =
                        ((view as RecyclerView).layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                    Timber.d("lastVisibleItem = $lastVisibleItem")
                    if (!viewModel.dataState.isReached && !viewModel.dataState.isCallingAPI &&
                        lastVisibleItem == adapterUsers.itemCount.minus(1)
                    )
                        binding.root.safeCallBack {
                            //binding.progressBar.visible()
                            viewModel.loadMore()
                        }
                } catch (ex:Exception){
                    Timber.e("${this::class.java} - ${ex.message}")
                }

            }

        }
    }

    override fun bindShareFlow(viewEvent: BaseViewEvent) {
        when (viewEvent) {
            else -> {

            }
        }
    }

    override fun bindStateFlow(viewState: BaseViewState) {
        when (viewState) {

            is HomeTab1ViewModel.ViewState.ListUser -> {
                viewState.payload.contentIfNotHandled?.let {
                    adapterUsers.submitList(it)
                }
            }

            else -> {

            }
        }
    }
}