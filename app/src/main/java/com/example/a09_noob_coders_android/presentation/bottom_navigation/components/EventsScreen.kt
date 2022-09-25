package com.example.a09_noob_coders_android.presentation.bottom_navigation.components

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Code
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.a09_noob_coders_android.MasterViewModel
import com.example.a09_noob_coders_android.data.models.AllEventModel
import com.example.a09_noob_coders_android.data.repository.MainRepository
import com.example.a09_noob_coders_android.ui.theme.BluePrimary
import com.example.a09_noob_coders_android.ui.theme.BlueSecondary
import com.example.a09_noob_coders_android.ui.theme.OrangeDark
import kotlinx.coroutines.Job


@Composable
fun EventsScreen(navController: NavHostController, context: Context) {
    val job = Job()
    val vm = MasterViewModel(job)
    vm.getAllData(context, MainRepository(), job)
    vm.allEventsList?.let { EventFronted(it, navController, context) }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EventFronted(
    allEventsList: SnapshotStateList<AllEventModel>,
    navController: NavHostController,
    context: Context
) {

    val scrollstate = rememberLazyListState()

    Scaffold(
        topBar = {
            Text(
                text = "All Contests",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BluePrimary)
                    .padding(10.dp),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        },
        backgroundColor = BluePrimary
    ) {
        Box(modifier = Modifier.padding(it)) {
            if (allEventsList.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = scrollstate
                ) {

                    val in24hrs = allEventsList.filter { it.in_24_hours == "Yes" }
                    val notin24hrs = allEventsList.filter { it.in_24_hours == "No" }

                    stickyHeader {
                        Text(
                            text = "In 24 hours",
                            modifier = Modifier
                                .background(BluePrimary)
                                .padding(15.dp)
                                .fillMaxWidth(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    if (in24hrs.isEmpty()) {
                        item {
                            Text(
                                text = "No contests",
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp)
                            )
                        }
                    }

                    items(in24hrs.size) { index ->
                        EventItem(
                            contestName = in24hrs[index].name,
                            startingTime = in24hrs[index].start_time,
                            endingTime = in24hrs[index].end_time,
                            eventDate = in24hrs[index].in_24_hours,
                            eventURL = in24hrs[index].url
                        )
                    }


                    stickyHeader {
                        Text(
                            text = "Other contests",
                            modifier = Modifier
                                .background(BluePrimary)
                                .padding(15.dp)
                                .fillMaxWidth(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    if (notin24hrs.isEmpty()) {
                        item {
                            Text(
                                text = "No contests",
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp)
                            )
                        }
                    }

                    items(notin24hrs.size) { index ->
                        EventItem(
                            contestName = notin24hrs[index].name,
                            startingTime = notin24hrs[index].start_time,
                            endingTime = notin24hrs[index].end_time,
                            eventDate = notin24hrs[index].in_24_hours,
                            eventURL = notin24hrs[index].url
                        )
                    }
                }
            }
        }
    }
}

// Event composable
@Composable
fun EventItem(
    contestName: String = "Contest Name",
    startingTime: String = "20:00",
    endingTime: String = "23:00",
    eventDate: String = "20.02.2020",
    eventURL: String = ""
) {
    val uriHandler = LocalUriHandler.current
    Card(
        shape = RoundedCornerShape(10.dp),
        backgroundColor = BlueSecondary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                uriHandler.openUri(eventURL)
            }
    ) {
        Column {
            Spacer(modifier = Modifier.padding(bottom = 10.dp))
            Icon(
                imageVector = Icons.Rounded.Code,
                contentDescription = "Code Icon",
                modifier = Modifier
                    .padding(10.dp, bottom = 10.dp)
                    .height(50.dp)
                    .width(50.dp),
                tint = OrangeDark
            )
            Text(
                text = contestName,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 10.dp),
                color = Color.White,
            )
            Spacer(modifier = Modifier.padding(bottom = 2.dp))
            Text(
                text = "${startingTime.slice(11..15)} UTC - ${endingTime.slice(11..15)} UTC",
                color = Color.White,
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.padding(bottom = 20.dp))
            Text(
                text = "${startingTime.slice(0..9)} - ${endingTime.slice(0..9)}",
                modifier = Modifier.padding(start = 10.dp),
                color = Color.White,

                )
            Spacer(modifier = Modifier.padding(bottom = 10.dp))
        }
    }
}
