package com.example.a09_noob_coders_android.presentation.news_components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.a09_noob_coders_android.data.models.news_modals.Article
import com.example.a09_noob_coders_android.ui.theme.BluePrimary

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsItem(
    context: Context,
    article: Article,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onClick,
        backgroundColor = BluePrimary
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)

        ) {

            val painter = rememberImagePainter(article.urlToImage)
            Image(
                painter = painter,
                contentDescription = "",
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    .padding(4.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(4.dp)
            ) {
                Text(text = article.title.toString(), color = Color.White, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = article.source?.name.toString(), color = Color.Gray, fontSize = 9.sp)
            }
        }
    }
}