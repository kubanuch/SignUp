package com.kubanych.signup.exeption

import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

fun <T : Any> Fragment.setBackStackData(key: String, data: (T), doBack: Boolean) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, data)
    if (doBack) {
        findNavController().navigateUp()
    }
}

fun <T : Any> DialogFragment.setBackStackDataWithDialog(key: String, data: T, doBack: Boolean) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, data)
    if (doBack) {
        dialog?.dismiss()
    }
}

fun <T : Any> Fragment.getBackStackData(key: String, result: (T) -> (Unit)) {
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)
        ?.observe(viewLifecycleOwner) {
            result(it)
        }
}

fun NavController.navigateSafely(@IdRes actionId: Int) {
    currentDestination?.getAction(actionId)?.let { navigate(actionId) }
}

fun NavController.navigateSafely(directions: NavDirections) {
    currentDestination?.getAction(directions.actionId)?.let { navigate(directions) }
}