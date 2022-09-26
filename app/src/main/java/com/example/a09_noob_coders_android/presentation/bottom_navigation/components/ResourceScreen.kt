package com.example.a09_noob_coders_android.presentation.bottom_navigation.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.a09_noob_coders_android.presentation.resource_module.modals.ResourceModal
import com.example.a09_noob_coders_android.ui.theme.BluePrimary
import com.example.a09_noob_coders_android.presentation.resource_module.components.AddResourceBottomSheet
import com.example.a09_noob_coders_android.presentation.resource_module.components.ResourceItem
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ResourceScreen(
    navController: NavHostController,
    context: Context
) {

    val db = Firebase.firestore
    val currentUser = Firebase.auth.currentUser

    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()

    val refreshList = remember {
        mutableStateOf(true)
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Text(
                    text = "Resources",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f)
                )

                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add Resource",
                    tint = Color.White,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(10.dp)
                        .clickable {
                            scope.launch {
                                sheetState.expand()
                            }
                        }
                )
            }
        },
        sheetContent = {
            AddResourceBottomSheet(context, sheetState, refreshList)
        },
        backgroundColor = BluePrimary,
        sheetPeekHeight = 0.dp,
        modifier = Modifier.fillMaxSize()
    ) {

        val li = remember {
            mutableStateOf<QuerySnapshot?>(null)
        }

        if (refreshList.value) {
            db.collection("resources").get().addOnSuccessListener { resources ->
                li.value = resources
            }
            refreshList.value = false
        }

        LazyColumn() {
            if (li.value != null) {
                items(li.value?.documents!!.size) { index ->
                    ResourceItem(
                        context,
                        ResourceModal(
                            title = li.value?.documents!![index]["title"] as String,
                            type = li.value?.documents!![index]["type"] as String,
                            tags = li.value?.documents!![index]["tags"] as String,
                            desc = li.value?.documents!![index]["desc"] as String,
                            url = li.value?.documents!![index]["url"] as String,
                            creator = li.value?.documents!![index]["creator"] as String,
                            upvotes = li.value?.documents!![index]["upvotes"] as Long,
                            downvotes = li.value?.documents!![index]["downvotes"] as Long
                        )
                    )
                    Divider()
                }
            }
        }
    }
}