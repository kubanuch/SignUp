package com.kubanych.signup.presentation.ui.fragment.auth.profile

import android.Manifest
import android.net.Uri
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.kubanych.signup.R
import com.kubanych.signup.base.BaseFragment
import com.kubanych.signup.base.LanguagesModel
import com.kubanych.signup.databinding.FragmentProfileBinding
import com.kubanych.signup.exeption.hasPermissionCheckAndRequest
import com.kubanych.signup.exeption.setImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment :
    BaseFragment<FragmentProfileBinding, ProfileViewModel>(R.layout.fragment_profile) {
    override val binding by viewBinding(FragmentProfileBinding::bind)
    override val viewModel: ProfileViewModel by viewModels()
    private lateinit var uri: Uri
    private lateinit var db: FirebaseFirestore
    private lateinit var sr: FirebaseStorage

    override fun setupListeners() {

        db = FirebaseFirestore.getInstance()
        binding.btnProfile.setOnClickListener {
            val name = binding.inputEditTv.text.toString()
            val fname = binding.inputEditText.text.toString()
            val user = LanguagesModel(name, fname, image = null)

            db.collection("users").document(" Мой документь").set(user).addOnSuccessListener {
                Toast.makeText(requireContext(), "Успешно", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }

        }
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { isGranted ->
            for (permission in isGranted) {
                when {
                    permission.value -> fileChooserContract.launch("image/*")
                    !shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                        permissionMessage()

                    }
                }
            }
        }

        binding.imageOfProfile.setOnClickListener {
            if (hasPermissionCheckAndRequest(
                    requestPermissionLauncher,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                )
            ) {
                fileChooserContract.launch("image/*")
            }
        }

    }
    private fun permissionMessage() {
        if (findNavController().currentDestination?.id != R.id.permissionErrorBottomSheet) {
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToPermissionErrorBottomSheet(
                    "Приложение GeekMessenger не может функционировать без разрешение на галерею устройства. Вы можете включить их в разределе Настройки"
                )
            )
        }
    }


    private val fileChooserContract =
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri ->
            if (imageUri != null) {
                binding.imageOfProfile.setImage(imageUri.toString())
                uri = imageUri
            }
        }
}







