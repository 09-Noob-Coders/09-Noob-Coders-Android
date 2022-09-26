package com.example.a09_noob_coders_android.presentation.jobs_components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a09_noob_coders_android.data.models.job_models.JobModalItem
import com.example.a09_noob_coders_android.ui.theme.BluePrimary
import com.example.a09_noob_coders_android.ui.theme.OrangeDark

@Composable
fun JobsItem(
    context: Context,
    item: JobModalItem
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
//            .height(120.dp),
        backgroundColor = BluePrimary
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(
                text = item.jobtitle,
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = item.companyname, fontSize = 15.sp, color = OrangeDark)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = item.companylocation, fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    val redirect = Intent(Intent.ACTION_VIEW)
                    redirect.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    redirect.data = Uri.parse(item.urllink)
                    context.startActivity(redirect)
                }, colors = ButtonDefaults.buttonColors(backgroundColor = OrangeDark)
            ) {
                Text(text = "Apply Now!", color = Color.White)
            }
        }
    }
}