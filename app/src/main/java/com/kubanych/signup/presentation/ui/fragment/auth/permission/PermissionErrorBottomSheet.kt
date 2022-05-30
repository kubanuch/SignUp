package com.kubanych.signup.presentation.ui.fragment.auth.permission

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kubanych.signup.databinding.PermissionErrorBottomSheetBinding

class PermissionErrorBottomSheet : BottomSheetDialogFragment() {

    private var _binding: PermissionErrorBottomSheetBinding? = null
    private val args: PermissionErrorBottomSheetArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PermissionErrorBottomSheetBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initialize()
        setupListeners()
    }

    private fun initialize() {
        _binding?.txtDialogPermissionTitle?.text = args.title
    }

    private fun setupListeners() {
        setupClickCancel()
        setupClickClose()
    }

    private fun setupClickCancel() {
        _binding?.btnSettings?.setOnClickListener {
            startActivity(
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(
                    Uri.parse("package:${requireActivity().packageName}")
                )
            )
            dismiss()
        }
    }

    private fun setupClickClose() {
        _binding?.btnCancel?.setOnClickListener { findNavController().navigateUp() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}