package com.kubanych.signup.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitsu.domain.either.Either
import com.kubanych.signup.presentation.ui.state.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected fun <T> mutableUIStateFlow() = MutableStateFlow<UIState<T>>(UIState.Idle())

    protected fun <T, S> Flow<Either<String, T>>.collectRequest(
        state: MutableStateFlow<UIState<S>>,
        mapData: (data: T) -> S
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            state.value = UIState.Loading()
            this@collectRequest.collect {
                when (it) {
                    is Either.Left -> state.value = UIState.Error(it.value)
                    is Either.Right -> state.value = UIState.Success(mapData(it.value))
                }
            }
        }
    }
}