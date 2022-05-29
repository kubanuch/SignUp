package com.kubanych.signup.presentation.ui.fragment.auth.home

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.kubanych.signup.R
import com.kubanych.signup.base.BaseFragment
import com.kubanych.signup.databinding.FragmentHomeBinding
import com.kubanych.signup.exeption.getBackStackData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val binding by viewBinding(FragmentHomeBinding::bind)
    private val args by navArgs<HomeFragmentArgs>()
    override val viewModel: HomeViewModel by viewModels()
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mVerificationId: String
    private lateinit var countdownTimer: CountDownTimer
    private var timeSeconds = 0L


    @SuppressLint("SetTextI18n")
    override fun setupListeners() {
        binding.tvNumber.text = "код был отправлен на номер:${args.numberOne}"
        getBackStackData<Boolean>("key") {
            if (it) {
                binding.viewTimer.text = args.number.toString()
            }else{
            }



        }
        timer()
        binding.tvCode.setOnClickListener {
            binding.viewTimer.isVisible = true
            binding.tvCode.isVisible = false
            timer()
        }
        binding.btnSignIn.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDialogFragment2(
                    "",
                    false
                )
            )
        }

    }

    //    override fun setupListeners() {
//        binding.btnSignIn.setOnClickListener {
//            val number: String = binding.etTextOne.text.toString()
//            verifyPhoneNumberWithCode(number, mVerificationId)
//        }
//
//    }
    private fun timer() {
        countdownTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(p0: Long) {
                timeSeconds = p0
                updateTextUI()
            }

            override fun onFinish() {
                countdownTimer.cancel()
                binding.viewTimer.isVisible = false
                binding.tvCode.isVisible = true
            }
        }
        countdownTimer.start()
    }

    private fun updateTextUI() {
        val minute = (timeSeconds / 1000) / 60
        val seconds = (timeSeconds / 1000) % 60
        if (seconds <= 9) {
            "Отправить код  заново 0$minute:0$seconds".also { binding.viewTimer.text = it }
        } else {
            "Отправить код  заново 0$minute:$seconds".also { binding.viewTimer.text = it }
        }

    }


    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithPhoneAuthCredential(credential)
    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
                    task.result.user
                    Navigation.findNavController(requireView()).navigate(R.id.profileFragment)
                } else {
                    Toast.makeText(requireContext(), "noo data", Toast.LENGTH_SHORT).show()
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(requireContext(), "anime", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

}
