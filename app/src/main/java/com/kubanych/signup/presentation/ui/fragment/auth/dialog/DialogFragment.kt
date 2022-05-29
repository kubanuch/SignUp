package com.kubanych.signup.presentation.ui.fragment.auth.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
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
    private lateinit var countdownTimer: CountDownTimer
    private var timeSeconds = 0L
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
            } else {
                tvResendCodeIn.text = "Вы неправильно ввели код 3 раза."
                tvResendCode.text = "Повторите попытку через 3 минуты"
                applyBtn.text = "Далее"
                applyButton.isVisible = false
                tvtText.isVisible = false
                timer()
            }
            setupButtonsClickListener()

        }
        return builder
    }

    private fun timer() {
        countdownTimer = object : CountDownTimer(120000, 1000) {
            override fun onTick(p0: Long) {
                timeSeconds = p0
                updateTextUI()
            }

            override fun onFinish() {
                countdownTimer.cancel()
            }
        }
        countdownTimer.start()
    }

    private fun updateTextUI() {
        val minute = (timeSeconds / 1000) / 60
        val seconds = (timeSeconds / 1000) % 60
        if (seconds <= 9) {
            "Отправить код  заново 0$minute:0$seconds".also { binding.applyButton.text = it }
        } else {
            "Отправить код  заново 0$minute:$seconds".also { binding.applyButton.text = it }
        }
    }

    private fun setupButtonsClickListener() {
        binding.applyBtn.setOnClickListener {
            setBackStackData("next", data = true, doBack = true)
        }

        binding.applyButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}
