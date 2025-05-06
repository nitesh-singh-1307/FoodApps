plugins {
//    alias(libs.plugins.android.application)
//    alias(libs.plugins.kotlin.android)
//    id("org.jetbrains.kotlin.plugin.compose") version "2.1.20" apply false
//    alias(libs.plugins.hilt.android) apply false
//    id("org.jetbrains.kotlin.kapt") // Apply the Kapt plugin
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.google.services)
    id("com.google.devtools.ksp") version "2.1.20-1.0.32"

//    kotlin("jvm")
//    alias(libs.plugins.ksp) // <<< ADD THIS LINE to apply the KSP plugin


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
        multiDexEnabled = true
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
    packaging {
        resources {
            pickFirsts.add("META-INF/versions/9/OSGI-INF/MANIFEST.MF")
        }
    }
    compileOptions {
        val javaVersion = libs.versions.javaVersion.get()
        sourceCompatibility = JavaVersion.toVersion(javaVersion)
        targetCompatibility = JavaVersion.toVersion(javaVersion)

    }
    kotlinOptions {
        jvmTarget = libs.versions.javaVersion.get()
    }

    buildFeatures {
        compose = true
    }


    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }

    configurations.all {
        resolutionStrategy {
            force("androidx.test.espresso:espresso-core:3.5.1")        }
    }



}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.bundles.androidx)
    implementation(libs.bundles.compose)
    implementation(libs.androidx.ui)
    implementation(libs.datastore.preferences)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.composeMaterial)
//    implementation(libs.material3)
    implementation(libs.androidx.compose.testing)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.multidex)
    implementation(libs.compose.runtime)
    implementation(libs.retrofit.gson.converter)
    implementation(libs.okhttp3.logging.interceptor)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.coil.kt.compose)


    // Material3
//    implementation(libs.material3WindowSize)
//    implementation(libs.androidx.compose.material) // Add this line
    implementation(libs.bundles.hilt)
    ksp(libs.hilt.compiler)    // <<< ADD KSP configuration
    testImplementation(libs.androidx.junit)
    testImplementation(libs.androidx.espresso.core)
}
