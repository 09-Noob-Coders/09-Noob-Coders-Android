package com.example.a09_noob_coders_android.presentation.resource_module.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a09_noob_coders_android.presentation.resource_module.modals.ResourceModal
import com.example.a09_noob_coders_android.ui.theme.BluePrimary
import com.example.a09_noob_coders_android.ui.theme.OrangeDark
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ResourceItem(
    context: Context,
    resource: ResourceModal,
    refreshList: MutableState<Boolean>
) {

    val db = Firebase.firestore
    val currentUser = Firebase.auth.currentUser

    val marked = remember {
        mutableStateOf(false)
    }


    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        backgroundColor = BluePrimary,
        onClick = {
            val redirect = Intent(Intent.ACTION_VIEW)
            redirect.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            redirect.data = Uri.parse(resource.url)
            context.startActivity(redirect)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = resource.title,
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = resource.creator, fontSize = 15.sp, color = OrangeDark)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "", modifier = Modifier.weight(1f))
                Text(
                    text = resource.upvoters.size.toString(),
                    color = Color.Green,
                    modifier = Modifier.padding(3.dp)
                )

                if (!resource.upvoters.contains(currentUser?.uid.toString())) {
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowUp,
                        contentDescription = "",
                        tint = Color.Green,
                        modifier = Modifier
                            .padding(3.dp)
                            .clickable {
                                var docId = ""
                                db
                                    .collection("resources")
                                    .get()
                                    .addOnSuccessListener {
                                        for (i in it.documents) {
                                            if (i["title"] == resource.title) {
                                                docId = i.id
                                                val upList = i["upvoters"] as MutableList<String>
                                                val downList =
                                                    i["downvoters"] as MutableList<String>

                                                if (!upList.contains(currentUser?.uid.toString()) && downList.contains(
                                                        currentUser?.uid.toString()
                                                    )
                                                ) {
                                                    upList.add(currentUser?.uid.toString())
                                                    downList.remove(currentUser?.uid.toString())
                                                    db
                                                        .collection("resources")
                                                        .document(docId)
                                                        .update(
                                                            mapOf(
                                                                "upvoters" to upList,
                                                                "downvoters" to downList
                                                            )
                                                        )
                                                } else if (!upList.contains(currentUser?.uid.toString()) && !downList.contains(
                                                        currentUser?.uid.toString()
                                                    )
                                                ) {
                                                    upList.add(currentUser?.uid.toString())
                                                    db
                                                        .collection("resources")
                                                        .document(docId)
                                                        .update(
                                                            mapOf(
                                                                "upvoters" to upList,
                                                                "downvoters" to downList
                                                            )
                                                        )
                                                }
                                            }
                                        }
                                    }
                                refreshList.value = true
                            }
                    )
                } else {
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowUp,
                        contentDescription = "",
                        tint = Color.Gray,
                        modifier = Modifier
                            .padding(3.dp)
                    )
                }

                Text(
                    text = resource.downvoters.size.toString(),
                    color = Color.Red,
                    modifier = Modifier.padding(3.dp)
                )


                if (!resource.downvoters.contains(currentUser?.uid.toString())) {
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = "",
                        tint = Color.Red,
                        modifier = Modifier
                            .padding(3.dp)
                            .clickable {
                                var docId = ""
                                db
                                    .collection("resources")
                                    .get()
                                    .addOnSuccessListener {
                                        for (i in it.documents) {
                                            if (i["title"] == resource.title) {
                                                docId = i.id
                                                val upList = i["upvoters"] as MutableList<String>
                                                val downList =
                                                    i["downvoters"] as MutableList<String>

                                                if (!downList.contains(currentUser?.uid.toString()) && upList.contains(
                                                        currentUser?.uid.toString()
                                                    )
                                                ) {
                                                    upList.remove(currentUser?.uid.toString())
                                                    downList.add(currentUser?.uid.toString())
                                                    db
                                                        .collection("resources")
                                                        .document(docId)
                                                        .update(
                                                            mapOf(
                                                                "upvoters" to upList,
                                                                "downvoters" to downList
                                                            )
                                                        )
                                                } else if (!downList.contains(currentUser?.uid.toString()) && !upList.contains(
                                                        currentUser?.uid.toString()
                                                    )
                                                ) {
                                                    downList.add(currentUser?.uid.toString())
                                                    db
                                                        .collection("resources")
                                                        .document(docId)
                                                        .update(
                                                            mapOf(
                                                                "upvoters" to upList,
                                                                "downvoters" to downList
                                                            )
                                                        )
                                                }
                                            }
                                        }
                                    }
                                refreshList.value = true
                            }
                    )
                } else {
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = "",
                        tint = Color.Gray,
                        modifier = Modifier
                            .padding(3.dp)
                    )
                }
            }
        }
    }
}
