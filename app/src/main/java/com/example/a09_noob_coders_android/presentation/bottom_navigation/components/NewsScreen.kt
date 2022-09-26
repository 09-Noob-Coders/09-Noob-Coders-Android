package com.example.a09_noob_coders_android.presentation.bottom_navigation.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.a09_noob_coders_android.data.models.news_modals.Article
import com.example.a09_noob_coders_android.data.repository.Repo
import com.example.a09_noob_coders_android.presentation.news_components.NewsVM
import com.example.a09_noob_coders_android.ui.theme.BluePrimary
import com.example.a09_noob_coders_android.ui.theme.BlueSecondary
import com.example.a09_noob_coders_android.ui.theme.OrangeDark
import com.example.a09_noob_coders_android.ui.theme.OrangeLite
import com.example.docode.test.news_module.news_components.NewsItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsScreen(
    navController: NavHostController,
    context: Context
) {

    val job = Job()
    val vm = NewsVM(job)
    vm.getNewsFunc(context, Repo(), job)

    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()

    val selectedArticle = remember { mutableStateOf<Article?>(null) }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            if (selectedArticle.value != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((LocalConfiguration.current.screenHeightDp).dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    val painter = rememberImagePainter(selectedArticle.value!!.urlToImage)
                    Image(
                        painter = painter,
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((LocalConfiguration.current.screenHeightDp * 0.27).dp)
                    )

                    Column(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = selectedArticle.value!!.title.toString(),
                            fontSize = 20.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = selectedArticle.value!!.source?.name.toString(),
                            fontSize = 15.sp,
                            color = OrangeDark
                        )
                        Spacer(modifier = Modifier.height(15.dp))

                        var contentText = ""
                        if (selectedArticle.value!!.description == null && selectedArticle.value!!.content == null) {
                            contentText = "NA"
                        } else if (selectedArticle.value!!.description == null) {
                            contentText = selectedArticle.value!!.content!!
                        } else {
                            contentText = selectedArticle.value!!.description!!
                        }

                        Text(
                            text = contentText,
                            fontSize = 18.sp,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        Row {
                            Text(text = "", modifier = Modifier.weight(1f))
                            Text(
                                text = "~ ${selectedArticle.value!!.author.toString()}",
                                fontSize = 15.sp,
                                color = Color.White,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = {
                            val redirect = Intent(Intent.ACTION_VIEW)
                            redirect.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            redirect.data = Uri.parse(selectedArticle.value!!.url)
                            context.startActivity(redirect)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = OrangeLite),
                        shape = RoundedCornerShape(10.dp),
                    ) {
                        Text(
                            text = "Read Full Article",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        },
        sheetBackgroundColor = BlueSecondary,
        sheetPeekHeight = 0.dp,
    ) {
        Column(
            modifier = Modifier
                .scrollable(
                    rememberScrollState(),
                    orientation = Orientation.Vertical
                )
                .background(BluePrimary)
        ) {

            // Title
            Text(
                text = "News",
                fontSize = 30.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                fontWeight = FontWeight.Bold
            )

            // News List
            val articleLi = vm._getNews
            LazyColumn() {
                items(count = articleLi?.size!!) { item ->
                    NewsItem(article = articleLi[item], context = context, onClick = {
                        selectedArticle.value = articleLi[item]
                        scope.launch {
                            sheetState.expand()
                        }
                        Toast.makeText(
                            context,
                            "Swipe down to close the preview!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    )
                }
            }
        }
    }
}