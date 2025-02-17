package com.example.foodapps.prasentation.welcomescreen

sealed class WelcomeScreenEvent{
    data object SaveAppEntry : WelcomeScreenEvent()
}