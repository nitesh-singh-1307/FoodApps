// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
//    kotlin("jvm") version "2.1.10" apply false
//    alias(libs.plugins.ksp) apply false
    id("com.google.devtools.ksp") version "2.1.20-1.0.32" apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.hilt.android) apply false // Apply the Hilt plugin using the alias

//    id("com.android.application") version "8.4.0-alpha14" apply false
//    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
//    id("com.google.dagger.hilt.android") version "2.51" apply false
}