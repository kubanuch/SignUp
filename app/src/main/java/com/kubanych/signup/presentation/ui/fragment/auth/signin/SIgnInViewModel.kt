package com.kubanych.signup.presentation.ui.fragment.auth.signin

import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.kubanych.signup.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class SignInViewModel @Inject constructor() : BaseViewModel() {
//
//    mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//        override fun onVerificationCompleted(credential: PhoneAuthCredential) {}
//
//        override fun onVerificationFailed(e: FirebaseException) {
//            if (e is FirebaseAuthInvalidCredentialsException) {
//                Toast.makeText(requireContext(), "Неверный запрос", Toast.LENGTH_SHORT).show()
//            } else if (e is FirebaseTooManyRequestsException) {
//                Toast.makeText(requireContext(), "anime", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        override fun onCodeSent(
//            verificationId: String,
//            token: PhoneAuthProvider.ForceResendingToken
//        ) {
//            Log.d(TAG, "onCodeSent:$verificationId")
//            mVerificationId = verificationId
//        }
//    }

}