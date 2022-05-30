plugins {

    // Application
    id(libs.plugins.agp.application.get().pluginId)

    // Kotlin
    id("kotlin-android")

    // Kapt
    id("kotlin-kapt")

    // Navigation SafeArgs
    id(libs.plugins.navigation.safeArgs.get().pluginId)

    // Hilt
    id(libs.plugins.hilt.android.get().pluginId)

    id("com.google.gms.google-services") version "4.3.10"
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.kubanych.signup"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    //ViewBinding
    buildFeatures.viewBinding = true
}

dependencies {

    implementation(project(":domain"))
    implementation(project(":data"))
    // Firebase
    implementation("com.google.firebase:firebase-auth-ktx:21.0.5")
    // firebaseFirestore
    implementation("com.google.firebase:firebase-firestore-ktx:24.1.2")
    implementation (platform("com.google.firebase:firebase-bom:30.1.0"))

    implementation ("com.google.firebase:firebase-storage-ktx")
    // UI Components
    implementation(libs.bundles.uiComponents)

    // Core
    implementation(libs.android.core)

    // Coroutines
    implementation(libs.coroutines.android)

    // Lifecycle
    implementation(libs.bundles.lifecycle)
    // Paging
    implementation(libs.paging.paging)


    // Navigation
    implementation(libs.bundles.navigation)
    implementation("com.github.pinball83:masked-edittext:1.0.4")


    // Hilt
    implementation(libs.hilt.android)
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    kapt(libs.hilt.compiler)
}