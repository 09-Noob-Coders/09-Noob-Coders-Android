package com.example.docode.presentation.bottom_navigation.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.a09_noob_coders_android.MasterViewModel
import com.example.a09_noob_coders_android.data.repository.MainRepository
import com.example.a09_noob_coders_android.data.repository.Repo
import com.example.a09_noob_coders_android.presentation.bottom_navigation.components.EventItem
import com.example.a09_noob_coders_android.presentation.jobs_components.JobsItem
import com.example.a09_noob_coders_android.presentation.news_components.NewsItem
import com.example.a09_noob_coders_android.presentation.news_components.NewsVM
import com.example.a09_noob_coders_android.ui.theme.BluePrimary
import com.example.a09_noob_coders_android.ui.theme.OrangeDark
import com.example.docode.test.jobs_module.JobsVM
import kotlinx.coroutines.Job

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(navController: NavHostController, context: Context) {

    // News
    val newsJob = Job()
    val news_vm = NewsVM(newsJob)
    news_vm.getNewsFunc(context, Repo(), newsJob)


    // Jobs
    val query = remember {
        mutableStateOf("SDE Jobs India")
    }
    val job = Job()
    val vm = JobsVM(job)
    vm.getJobs(context, Repo(), job, query)


    // Contests
    val eventsJob = Job()
    val events_vm = MasterViewModel(eventsJob)
    events_vm.getAllData(context, MainRepository(), eventsJob)


    HomeUi(news_vm, events_vm, vm, context)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeUi(news_vm: NewsVM, events_vm: MasterViewModel, vm: JobsVM, context: Context) {
    LazyColumn(
        state = rememberLazyListState(),
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 15.dp, end = 15.dp)
            .background(BluePrimary)
    ) {
        item {
            Text(
                text = "Upcoming contest",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(30.dp))

            if (events_vm.allEventsList?.isNotEmpty() == true) {
                val in24hrs = events_vm.allEventsList.filter { it.in_24_hours == "Yes" }
                    .sortedBy { it.start_time }
                EventItem(
                    contestName = in24hrs[0].name,
                    startingTime = in24hrs[0].start_time,
                    endingTime = in24hrs[0].end_time,
                    eventDate = in24hrs[0].in_24_hours,
                    eventURL = in24hrs[0].url
                )
            } else {
                CircularProgressIndicator(color = OrangeDark)
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Top stories",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        if (news_vm._getNews?.isNotEmpty() == true) {
            val newsList = news_vm._getNews.sortedBy { it.publishedAt }
            for (i in 0..2) {
                item {
                    NewsItem(context = context, article = newsList[i], onClick = {
                        val redirect = Intent(Intent.ACTION_VIEW)
                        redirect.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        redirect.data = Uri.parse(newsList[i].url)
                        context.startActivity(redirect)
                    })
                }
            }
        } else {
            item {
                CircularProgressIndicator(color = OrangeDark)
            }
        }

        item {
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Trending Jobs",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        if (vm.getJobs?.isNotEmpty() == true) {
            val jobsList = vm.getJobs
            for (i in 0..2) {
                item {
                    JobsItem(context = context, item = jobsList[i])
                }
            }
        } else {
            item {
                CircularProgressIndicator(color = OrangeDark)
            }
        }
    }
}
