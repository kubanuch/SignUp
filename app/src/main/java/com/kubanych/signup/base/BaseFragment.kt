package com.kubanych.signup.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import androidx.viewbinding.ViewBinding
import com.kubanych.signup.presentation.ui.state.UIState
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


abstract class BaseFragment<Binding : ViewBinding, ViewModel : BaseViewModel>(@LayoutRes layoutId: Int) :
    Fragment(
        layoutId
    ) {
    protected abstract val binding: Binding
    protected abstract val viewModel: ViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupViews()
        setupListeners()
        setupRequests()
        launchObservers()

    }

    protected open fun initialize() {
    }

    protected open fun setupViews() {
    }

    protected open fun setupListeners() {
    }


    protected open fun setupRequests() {

    }

    protected open fun launchObservers() {

    }

    protected fun <T : Any> Flow<PagingData<T>>.spectatePaging(
        lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
        success: suspend (data: PagingData<T>) -> Unit,
        error: ((error: String) -> Unit)? = null
    ) {
        FlowGather(lifecycleState) {
            collectLatest {
                success(it)
                error(it)
            }
        }
    }

    private fun FlowGather(
        lifecycleState: Lifecycle.State,
        gather: suspend () -> Unit,
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(lifecycleState) {
                gather()
            }
        }
    }

    protected fun <T> StateFlow<UIState<T>>.spectateUiState(
        lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
        success: ((data: T) -> Unit)? = null,
        loading: ((data: UIState.Loading<T>) -> Unit)? = null,
        error: ((error: String) -> Unit)?,
        idle: ((idle: UIState.Idle<T>) -> Unit)? = null,
        gatherIfSucceed: ((state: UIState<T>) -> Unit)? = null
    ) {
        safeFlowGather(lifecycleState) {
            collect {
                gatherIfSucceed?.invoke(it)
                when (it) {
                    is UIState.Idle -> {
                        idle?.invoke(it)
                    }
                    is UIState.Loading -> {
                        loading?.invoke(it)
                    }
                    is UIState.Error -> {
                        error?.invoke(it.error)
                    }
                    is UIState.Success -> {
                        success?.invoke(it.data)
                    }
                }
            }

        }

    }

    private fun safeFlowGather(
        lifecycleState: Lifecycle.State,
        gather: suspend () -> Unit
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(lifecycleState) {
                gather()
            }
        }
    }

    protected fun <T> UIState<T>.setupViewVisibility(
        group: ConstraintLayout,
        loader: CircularProgressIndicator,
        navigateIfSucceed: Boolean = false
    ) {
        fun showLoader(isShown: Boolean) {
            group.isVisible = isShown
            loader.isVisible = isShown
        }
        when (this) {
            is UIState.Idle -> {

            }
            is UIState.Loading -> {
                showLoader(true)
            }
            is UIState.Error -> {
                showLoader(false)

            }
            is UIState.Success -> {
                if (navigateIfSucceed) {
                    showLoader(true)
                } else {
                    showLoader(false)
                }
            }
        }
    }
}