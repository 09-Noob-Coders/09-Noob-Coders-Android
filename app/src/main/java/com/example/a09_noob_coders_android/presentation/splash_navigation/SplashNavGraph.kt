package com.example.a09_noob_coders_android.presentation.splash_navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SplashNavGraph(navController: NavHostController, context: Context) {
    NavHost(
        navController = navController,
        startDestination = SplashScreens.Splash.route
    ) {
        composable(route = SplashScreens.Splash.route) {
            AnimatedSplashScreen(navController = navController, context = context)
        }
    }
}