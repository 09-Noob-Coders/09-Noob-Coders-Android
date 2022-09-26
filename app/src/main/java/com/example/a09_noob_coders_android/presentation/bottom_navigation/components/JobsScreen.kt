package com.example.a09_noob_coders_android.presentation.bottom_navigation.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.a09_noob_coders_android.data.repository.Repo
import com.example.a09_noob_coders_android.presentation.jobs_components.JobsItem
import com.example.a09_noob_coders_android.ui.theme.BluePrimary
import com.example.a09_noob_coders_android.ui.theme.BlueSecondary
import com.example.a09_noob_coders_android.ui.theme.OrangeLite
import com.example.docode.test.jobs_module.JobsVM
import kotlinx.coroutines.Job

@Composable
fun JobsScreen(
    navController: NavHostController,
    context: Context
) {
    val query = remember {
        mutableStateOf("python developer")
    }

    val job = Job()
    val vm = JobsVM(job)
    vm.getJobs(context, Repo(), job, query)
    JobWidget(context, vm, query)
}

@Composable
fun JobWidget(context: Context, vm: JobsVM, query: MutableState<String>) {

    var myquery by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(start = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Jobs",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        },
        backgroundColor = BluePrimary
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {

            // Search Bar
            TextField(
                value = myquery,
                onValueChange = {
                    myquery = it
                },
                shape = RoundedCornerShape(40.dp),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "Search Button",
                        modifier = Modifier.clickable {
                            query.value = myquery
                            vm.getJobs(context, Repo(), Job(), query)
                            myquery = ""
                        }
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = BlueSecondary,
                    textColor = OrangeLite,
                    placeholderColor = OrangeLite,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(text = "Search for your dream job....")
                },
                singleLine = true,
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(5.dp))

            // Jobs List
            LazyColumn() {
                if (vm.getJobs != null) {
                    items(vm.getJobs.size) { index ->
                        JobsItem(context = context, item = vm.getJobs[index])
                        Divider()
                    }
                }
            }
        }
    }
}