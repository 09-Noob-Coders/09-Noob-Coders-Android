package com.example.a09_noob_coders_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.a09_noob_coders_android.presentation.bottom_navigation.BottomNavGraph
import com.example.a09_noob_coders_android.presentation.bottom_navigation.components.BottomBarScreen
import com.example.a09_noob_coders_android.presentation.settings.SettingsSheet
import com.example.a09_noob_coders_android.ui.theme.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


class EventsListActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoobCodersAndroidTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {

                    val systemUiController = rememberSystemUiController()
                    val useDarkIcons = MaterialTheme.colors.isLight

                    systemUiController.setSystemBarsColor(
                        color = BluePrimary,
                        darkIcons = useDarkIcons
                    )

                    val navController = rememberNavController()
                    val auth = Firebase.auth

                    val sheetState = rememberBottomSheetState(
                        initialValue = BottomSheetValue.Collapsed
                    )
                    val scaffoldState = rememberBottomSheetScaffoldState(
                        bottomSheetState = sheetState
                    )
                    val scope = rememberCoroutineScope()

                    BottomSheetScaffold(
                        scaffoldState = scaffoldState,
                        sheetContent = {
                            SettingsSheet(this@EventsListActivity, sheetState)
                        },
                        sheetBackgroundColor = BlueSecondary,
                        sheetPeekHeight = 0.dp,
                        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                    ) {
                        Scaffold(

                            topBar = {
                                Row(
                                    modifier = Modifier
                                        .padding(15.dp)
                                ) {

                                    Text(text = "", modifier = Modifier.weight(1f))

                                    Icon(
                                        imageVector = Icons.Rounded.Settings,
                                        contentDescription = "Settings",
                                        modifier = Modifier
                                            .clickable {
//                                                auth.signOut()
//                                                startActivity(
//                                                    Intent(
//                                                        this@EventsListActivity,
//                                                        LoginActivity::class.java
//                                                    )
//                                                )

                                                scope.launch {
                                                    sheetState.expand()
                                                }
                                            },
                                        tint = OrangeDark
                                    )
                                }
                            },
                            bottomBar = {
                                BottomBar(navController = navController)
                            },
                            backgroundColor = BluePrimary
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(it)
                            ) {
                                BottomNavGraph(
                                    navController = navController,
                                    this@EventsListActivity
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.AllContests,
        BottomBarScreen.Resources,
        BottomBarScreen.News,
        BottomBarScreen.Jobs,
//        BottomBarScreen.Sites,
//        BottomBarScreen.Info
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(backgroundColor = BlueSecondary, contentColor = OrangeLite) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Screen Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route)
        }
    )
}