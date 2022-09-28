package com.example.a09_noob_coders_android.presentation.bottom_navigation.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.a09_noob_coders_android.presentation.resource_module.ResourceItemClass
import com.example.a09_noob_coders_android.presentation.resource_module.modals.ResourceModal
import com.example.a09_noob_coders_android.ui.theme.BluePrimary
import com.example.a09_noob_coders_android.presentation.resource_module.components.AddResourceBottomSheet
import com.example.a09_noob_coders_android.presentation.resource_module.components.ResourceItem
import com.example.a09_noob_coders_android.ui.theme.BlueSecondary
import com.example.a09_noob_coders_android.ui.theme.OrangeLite
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

        if (refreshList.value || ResourceItemClass.upvoted.value || ResourceItemClass.downvoted.value) {
            db.collection("resources").get().addOnSuccessListener { resources ->
                li.value = resources
            }
            refreshList.value = false
            ResourceItemClass.upvoted.value = false
            ResourceItemClass.downvoted.value = false
        }

        val myquery = remember {
            mutableStateOf("")
        }
        TextField(
            value = myquery.value,
            onValueChange = {
                myquery.value = it
            },
            shape = RoundedCornerShape(40.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = BlueSecondary,
                textColor = OrangeLite,
                placeholderColor = Color.Gray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(text = "kotlin, swift, python....")
            },
            singleLine = true,
            modifier = Modifier
                .padding(start = 5.dp, end = 5.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(5.dp))


        LazyColumn() {
            if (li.value != null) {
                val newLi = li.value!!.documents.sortedByDescending { it["upvotes"] as Long }
                    .filter {
                        "${it["tags"].toString().lowercase()},${
                            it["title"].toString().lowercase()
                        }".contains(myquery.value.lowercase())
                    }
                items(newLi.size) { index ->
                    ResourceItem(
                        context,
                        ResourceModal(
                            title = newLi[index]["title"] as String,
                            type = newLi[index]["type"] as String,
                            tags = newLi[index]["tags"] as String,
                            desc = newLi[index]["desc"] as String,
                            url = newLi[index]["url"] as String,
                            creator = newLi[index]["creator"] as String,
                            upvotes = newLi[index]["upvotes"] as Long,
                            downvotes = newLi[index]["downvotes"] as Long,
                            upvoters = newLi[index]["upvoters"] as MutableList<String>,
                            downvoters = newLi[index]["downvoters"] as MutableList<String>
                        ),
                        refreshList
                    )
                    Divider()
                }
            }
        }
    }
}