package com.kubanych.signup.exeption

import android.content.pm.PackageManager
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.kubanych.signup.R

fun <T : Any> Fragment.setBackStackData(key: String, data: (T), doBack: Boolean) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, data)
    if (doBack) {
        findNavController().navigateUp()
    }
}

fun ImageView.setImage(uri: String) {
    Glide.with(this)
        .load(uri)
        .circleCrop()
        .error(R.drawable.icon_cel)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun Fragment.hasPermissionCheckAndRequest(
    requestPermissionLauncher: ActivityResultLauncher<Array<String>>,
    permission: Array<String>,
): Boolean {
    for (per in permission) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                per
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(permission)
            return false
        }
    }
    return true
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