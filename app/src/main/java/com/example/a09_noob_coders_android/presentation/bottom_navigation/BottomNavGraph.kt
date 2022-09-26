package com.example.a09_noob_coders_android.presentation.bottom_navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.a09_noob_coders_android.presentation.bottom_navigation.components.*
import com.example.a09_noob_coders_android.presentation.resource_module.ResourceScreen
import com.example.docode.presentation.bottom_navigation.components.HomeScreen

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    context: Context
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {

        composable(BottomBarScreen.Home.route) {
            HomeScreen(navController, context)
        }

        composable(BottomBarScreen.AllContests.route) {
            EventsScreen(navController = navController, context = context)
        }

        composable(BottomBarScreen.Resources.route) {
            ResourceScreen(navController = navController, context = context)
        }

        composable(BottomBarScreen.News.route) {
            NewsScreen(navController = navController, context = context)
        }

        composable(BottomBarScreen.Jobs.route) {
            JobsScreen(navController = navController, context = context)
        }
    }
}