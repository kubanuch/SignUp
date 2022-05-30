package com.kubanych.signup.presentation.ui.fragment.auth.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kubanych.signup.databinding.FragmentDialogBinding
import com.kubanych.signup.exeption.setBackStackData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogFragment : DialogFragment() {
    private lateinit var binding: FragmentDialogBinding
    private val args by navArgs<DialogFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.setCancelable(false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentDialogBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(activity)
            .setView(binding.root)
            .create()
        builder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.apply {
            if (args.isClicked) {
                tvNext.text = args.number
                tvResendCodeIn.isVisible = false
                tvResendCode.isVisible = false
                setupButtonsClickListener()
            } else {
                tvResendCodeIn.text = "Вы неправильно ввели код 3 раза."
                tvResendCode.text = "Повторите попытку через 3 минуты"
                applyBtn.text = "Далее"
                applyButton.isVisible = false
                tvtText.isVisible = false
                setupButtonsClick()
            }
        }
        return builder
    }

    private fun setupButtonsClick() {
        binding.applyBtn.setOnClickListener {
            setBackStackData("next", data = true, doBack = true)
        }
    }

    private fun setupButtonsClickListener() {
        binding.applyBtn.setOnClickListener {
            findNavController().navigate(
                DialogFragmentDirections.actionDialogFragmentToHomeFragment(
                    binding.tvNext.text.toString(), true, args.verificationId
                )
            )
        }

        binding.applyButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}
