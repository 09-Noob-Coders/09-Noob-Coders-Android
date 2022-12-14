package com.example.a09_noob_coders_android.data.repository

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.a09_noob_coders_android.data.models.AllEventModel
import com.example.a09_noob_coders_android.data.models.EventModel
import com.google.gson.Gson


class MainRepository() {

    suspend fun getAllEvents(context: Context, allEventsList: MutableLiveData<SnapshotStateList<AllEventModel>>): MutableLiveData<SnapshotStateList<AllEventModel>> {
        val gson = Gson()
        val url = "https://kontests.net/api/v1/all"
        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                Toast.makeText(context, "Fetched data successfully", Toast.LENGTH_LONG).show()
                for (i in 0 until response.length()) {
//                if (response.getJSONObject(i)["in_24_hours"] == "Yes") {
                    allEventsList.value?.add(
                        gson.fromJson(
                            response.getJSONObject(i).toString(),
                            AllEventModel::class.java
                        )
                    )
//                }
//                    Toast.makeText(
//                        context,
//                        response[i].toString(),
//                        Toast.LENGTH_LONG
//                    ).show()
                }
            }, {
                Toast.makeText(context, "Unable to fetch data!!!", Toast.LENGTH_LONG).show()
            }
        )
        queue.add(jsonObjectRequest)

        allEventsList.value?.sortBy { it.start_time }
        return allEventsList
    }
}

