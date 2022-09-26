package com.example.a09_noob_coders_android.presentation.bottom_navigation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.Feed
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector

const val ROOT_ROUTE = "ROOT_ROUTE"
const val PROFILE_ROUTE = "PROFILE_ROUTE"

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarScreen(
        "home",
        "Home",
        Icons.Rounded.Home
    )

    object AllContests : BottomBarScreen(
        "allcontests",
        "All Contests",
        Icons.Rounded.EmojiEvents
    )

    object Resources : BottomBarScreen(
        "resources",
        "Resource",
        Icons.Rounded.GridView
    )

    object News : BottomBarScreen(
        "news",
        "News",
        Icons.Rounded.Feed
    )

    object Jobs : BottomBarScreen(
        "jobs",
        "Jobs",
        Icons.Filled.Work
    )
}
