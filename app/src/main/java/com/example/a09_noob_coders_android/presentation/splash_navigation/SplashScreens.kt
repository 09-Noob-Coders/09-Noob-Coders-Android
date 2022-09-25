package com.example.a09_noob_coders_android.presentation.splash_navigation


sealed class SplashScreens(val route: String) {
    object Splash : SplashScreens("splash_screen")
}