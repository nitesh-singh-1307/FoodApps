
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android) // Apply the Hilt plugin using the alias
    kotlin("kapt")

}

android {
    namespace = "com.example.foodapps"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.foodapps"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
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
    buildFeatures {
        compose = true
    }
    defaultConfig {
        multiDexEnabled =  true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.datastore.preferences)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.multidex)
    implementation(libs.compose.runtime)
    api(libs.bundles.composes)
    api(libs.bundles.retrofit)
    implementation(libs.retrofit.gson.converter)
    implementation(libs.okhttp3.logging.interceptor)
//    implementation(platform(libs.androidx.compose.bom))
    // Material3
    implementation(libs.material3)
    implementation(libs.material3WindowSize)

    // Optional
//    implementation(libs.materialIcons)
    implementation(libs.material3Adaptive)
//    api(libs.bundles.composeBom)
    implementation(libs.hilt.navigation)
    implementation(libs.hilt.android) // Add the Hilt library using the alias
    kapt(libs.hilt.compiler) // Add the Hilt compiler using the alias
}