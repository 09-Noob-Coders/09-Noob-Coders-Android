package com.example.a09_noob_coders_android.presentation.resource_module.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a09_noob_coders_android.presentation.resource_module.modals.ResourceModal
import com.example.a09_noob_coders_android.ui.theme.BluePrimary
import com.example.a09_noob_coders_android.ui.theme.BlueSecondary
import com.example.a09_noob_coders_android.ui.theme.OrangeDark
import com.example.a09_noob_coders_android.ui.theme.OrangeLite
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddResourceBottomSheet(
    context: Context,
    sheetState: BottomSheetState,
    refreshList: MutableState<Boolean>
) {

    val db = Firebase.firestore
    val scope = rememberCoroutineScope()

    val author = remember {
        mutableStateOf("")
    }
    val title = remember {
        mutableStateOf("")
    }
    val tags = remember {
        mutableStateOf("")
    }
    val desc = remember {
        mutableStateOf("")
    }
    val link = remember {
        mutableStateOf("")
    }

    val typeDropDown = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .scrollable(rememberScrollState(), orientation = Orientation.Vertical)
            .background(BluePrimary)
            .padding(15.dp)
    ) {
        Row() {
            Text(
                text = "Contribute resource",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.clickable {
                    scope.launch {
                        sheetState.collapse()
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        // Author
        TextField(
            value = author.value,
            onValueChange = {
                author.value = it
            },
            shape = RoundedCornerShape(40.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = BlueSecondary,
                textColor = Color.Gray,
                placeholderColor = Color.Gray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = OrangeLite
            ),
            placeholder = {
                Text(text = "Author Name")
            },
            singleLine = true,
            modifier = Modifier
                .padding(start = 5.dp, end = 5.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))

        // Tags
        TextField(
            value = tags.value,
            onValueChange = {
                tags.value = it
            },
            shape = RoundedCornerShape(40.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = BlueSecondary,
                textColor = Color.Gray,
                placeholderColor = Color.Gray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = OrangeLite
            ),
            placeholder = {
                Text(text = "Tags(Separate tags by tags)")
            },
            singleLine = true,
            modifier = Modifier
                .padding(start = 5.dp, end = 5.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))

        // Resource Title
        TextField(
            value = title.value,
            onValueChange = {
                title.value = it
            },
            shape = RoundedCornerShape(40.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = BlueSecondary,
                textColor = Color.Gray,
                placeholderColor = Color.Gray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = OrangeLite
            ),
            placeholder = {
                Text(text = "Resource Title")
            },
            singleLine = true,
            modifier = Modifier
                .padding(start = 5.dp, end = 5.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))

        // Description
        TextField(
            value = desc.value,
            onValueChange = {
                desc.value = it
            },
            shape = RoundedCornerShape(20.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = BlueSecondary,
                textColor = Color.Gray,
                placeholderColor = Color.Gray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = OrangeLite
            ),
            placeholder = {
                Text(text = "Summary about the resource")
            },
            modifier = Modifier
                .padding(start = 5.dp, end = 5.dp)
                .fillMaxWidth()
                .height(200.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        // Type of the resource

        val options = listOf("Article", "Link", "Video", "Course")
        var expanded by remember { mutableStateOf(false) }
        val type = remember {
            mutableStateOf(options[0])
        }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
        ) {
            TextField(
                readOnly = true,
                value = type.value,
                onValueChange = {
                    type.value = it
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    backgroundColor = BlueSecondary,
                    textColor = Color.Gray,
                    placeholderColor = Color.Gray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = OrangeLite,
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(40.dp)
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            type.value = selectionOption
                            expanded = false
                        }
                    ) {
                        Text(text = selectionOption)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        // Link of the resource
        TextField(
            value = link.value,
            onValueChange = {
                link.value = it
            },
            shape = RoundedCornerShape(40.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = BlueSecondary,
                textColor = Color.Gray,
                placeholderColor = Color.Gray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = OrangeLite
            ),
            placeholder = {
                Text(text = "Resource Link")
            },
            singleLine = true,
            modifier = Modifier
                .padding(start = 5.dp, end = 5.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))

        // Submit button
        val submitFailed = remember {
            mutableStateOf(false)
        }
        Button(
            onClick = {
                if (title.value != "" && type.value != "" && tags.value != "" && desc.value != "" && link.value != "" && author.value != "") {
                    db.collection("resources").add(
                        ResourceModal(
                            title = title.value,
                            type = type.value,
                            tags = tags.value,
                            desc = desc.value,
                            url = link.value,
                            creator = author.value,
                            upvotes = 0,
                            downvotes = 0,
                            upvoters = mutableListOf(),
                            downvoters = mutableListOf(),
                        )
                    ).addOnSuccessListener {
                        scope.launch {
                            sheetState.collapse()
                            refreshList.value = true
                            Toast.makeText(
                                context,
                                "Resource added successfully 🙌",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }.addOnFailureListener {
//                    submitFailed.value = true
                        Toast.makeText(context, "Unable to add resource 😞", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
//                submitFailed.value = true
                    Toast.makeText(context, "Unable to add resource 😞", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = OrangeDark),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Submit",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}