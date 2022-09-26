package com.example.a09_noob_coders_android

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.a09_noob_coders_android.ui.theme.BluePrimary
import com.example.a09_noob_coders_android.ui.theme.BlueSecondary
import com.example.a09_noob_coders_android.ui.theme.OrangeLite
import com.example.a09_noob_coders_android.presentation.bottom_navigation.BottomNavGraph
import com.example.a09_noob_coders_android.presentation.bottom_navigation.components.BottomBarScreen
import com.example.a09_noob_coders_android.ui.theme.OrangeDark
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class EventsListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
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
                Scaffold(
                    topBar = {
                        Row(
                            modifier = Modifier
                                .padding(15.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBackIos,
                                contentDescription = "Logout",
                                modifier = Modifier
                                    .clickable {
                                        auth.signOut()
                                        startActivity(
                                            Intent(
                                                this@EventsListActivity,
                                                LoginActivity::class.java
                                            )
                                        )
                                    }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Logout",
                                color = OrangeDark,
                                fontSize = 16.sp
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
                        BottomNavGraph(navController = navController, this@EventsListActivity)
                    }
                }
            }
        }
    }
}

// Bottom bar composable
@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.AllContests,
        BottomBarScreen.Resources,
        BottomBarScreen.News,
        BottomBarScreen.Jobs,
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

// Bottom Sheet Item
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