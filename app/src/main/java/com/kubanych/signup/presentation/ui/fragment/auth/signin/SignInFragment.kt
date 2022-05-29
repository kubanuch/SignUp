package com.kubanych.signup.presentation.ui.fragment.auth.signin

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kubanych.signup.R
import com.kubanych.signup.base.BaseFragment
import com.kubanych.signup.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class SignInFragment :
    BaseFragment<FragmentSignInBinding, SignInViewModel>(R.layout.fragment_sign_in) {


    private lateinit var mCallbacks: OnVerificationStateChangedCallbacks
    override val binding by viewBinding(FragmentSignInBinding::bind)
    override val viewModel: SignInViewModel by viewModels()
    private lateinit var mAuth: FirebaseAuth
    private var mVerificationId: String? = null
    private val TAG = "PhoneAuthActivity"

    override fun initialize() {
        mAuth = Firebase.auth
    }

    override fun setupListeners() {
        binding.btnSign.setOnClickListener {
            initClickers()
            findNavController().navigate(
                SignInFragmentDirections.actionSignInFragmentToDialogFragment(
                    binding.maskedEditText.text.toString(),
                    true
                )
            )
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        mCallbacks = object : OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {}

            override fun onVerificationFailed(e: FirebaseException) {
                if (e is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(requireContext(), "Неверный запрос", Toast.LENGTH_SHORT).show()
                } else if (e is FirebaseTooManyRequestsException) {
                    Toast.makeText(requireContext(), "anime", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCodeSent(
                verificationId: String,
                token: ForceResendingToken
            ) {
                Log.d(TAG, "onCodeSent:$verificationId")
                mVerificationId = verificationId
            }
        }
    }

    private fun initClickers() {
        val number: String = binding.maskedEditText.text.toString()
        startPhoneNumberVerification(number)
    }


    override fun onStart() {
        super.onStart()
        mAuth.currentUser
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(mCallbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


}