package com.example.a09_noob_coders_android.presentation.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.a09_noob_coders_android.EventsListActivity
import com.example.a09_noob_coders_android.LoginActivity
import com.example.a09_noob_coders_android.ui.theme.BluePrimary
import com.example.a09_noob_coders_android.ui.theme.LightBlue
import com.example.a09_noob_coders_android.ui.theme.OrangeDark
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsSheet(context: EventsListActivity, sheetState: BottomSheetState) {

    val auth = Firebase.auth
    val appLogo = rememberImagePainter(com.example.a09_noob_coders_android.R.drawable.docodelogo)
    val kontestsLogo = rememberImagePainter(com.example.a09_noob_coders_android.R.drawable.kontests)
    val newsLogo = rememberImagePainter(com.example.a09_noob_coders_android.R.drawable.newsapi)
    val developerLogo = rememberImagePainter(com.example.a09_noob_coders_android.R.drawable.devesh)

    val scope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        item {
            Row() {
                Text(text = "", modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close",
                    modifier = Modifier
                        .padding(5.dp)
                        .clickable {
                            scope.launch { sheetState.collapse() }
                        }
                )
            }
        }

        item {
            Column() {
                Text(
                    text = "Settings",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .background(BluePrimary)
                        .padding(20.dp)
                ) {
                    Text(
                        text = "DoCode",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Image(
                        painter = appLogo,
                        contentDescription = "AppLogo",
                        modifier = Modifier
                            .width(100.dp)
                            .height(100.dp)
                            .clip(RoundedCornerShape(10.dp)),
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "DoCode is an app made by coders for coders!\nIt uses MVVM architecture, various apis to get data from and Jetpack Compose.")
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .background(BluePrimary)
                    .padding(20.dp)
            ) {
                Text(
                    text = "APIs",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(25.dp))
                Text(
                    text = "Kontests API",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(15.dp))
                Image(
                    painter = kontestsLogo,
                    contentDescription = "Kontests",
                    modifier = Modifier
                        .width(200.dp)
                        .height(100.dp)
                        .clip(RoundedCornerShape(10.dp)),
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "Kontests api provides the data about various contests from 8 different websites.")
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "News API",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(15.dp))
                Image(
                    painter = newsLogo,
                    contentDescription = "News",
                    modifier = Modifier
                        .width(200.dp)
                        .height(100.dp)
                        .clip(RoundedCornerShape(10.dp)),
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "News api provides the latest and trending news about the tech world.")
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .background(BluePrimary)
                    .padding(20.dp)
            ) {
                Text(
                    text = "Developer",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(15.dp))
                Image(
                    painter = developerLogo,
                    contentDescription = "DeveloperLogo",
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .clip(RoundedCornerShape(10.dp)),
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Devesh Shivane")
                Spacer(modifier = Modifier.height(15.dp))
                Divider()
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Follow me on Instagram",
                    color = LightBlue,
                    modifier = Modifier.clickable {
                        val redirect = Intent(Intent.ACTION_VIEW)
                        redirect.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        redirect.data = Uri.parse("https://www.instagram.com/devesh_1532/")
                        context.startActivity(redirect)
                    }
                )
                Spacer(modifier = Modifier.height(15.dp))
                Divider()
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Connect with me on LinkedIn",
                    color = LightBlue,
                    modifier = Modifier.clickable {
                        val redirect = Intent(Intent.ACTION_VIEW)
                        redirect.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        redirect.data = Uri.parse("https://www.linkedin.com/in/deveshshivane/")
                        context.startActivity(redirect)
                    }
                )
                Spacer(modifier = Modifier.height(15.dp))
                Divider()
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Checkout my Github",
                    color = LightBlue,
                    modifier = Modifier.clickable {
                        val redirect = Intent(Intent.ACTION_VIEW)
                        redirect.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        redirect.data = Uri.parse("https://github.com/DAS-15/")
                        context.startActivity(redirect)
                    }
                )
                Spacer(modifier = Modifier.height(15.dp))
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .background(BluePrimary)
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            auth.signOut()
                            context.startActivity(
                                Intent(
                                    context,
                                    LoginActivity::class.java
                                )
                            )
                        }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Logout,
                        contentDescription = "",
                        tint = OrangeDark
                    )
                    Text(text = "Logout", color = OrangeDark)
                }
            }
        }
    }

}