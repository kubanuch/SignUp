package com.kubanych.signup.presentation.ui.fragment.auth.profile

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kubanych.signup.R
import com.kubanych.signup.base.BaseFragment
import com.kubanych.signup.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment() :
    BaseFragment<FragmentProfileBinding, ProfileViewModel>(R.layout.fragment_profile) {
    override val binding by viewBinding(FragmentProfileBinding::bind)
    override val viewModel: ProfileViewModel by viewModels()
}