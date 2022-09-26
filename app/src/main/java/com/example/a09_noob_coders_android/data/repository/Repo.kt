package com.example.a09_noob_coders_android.data.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.a09_noob_coders_android.data.models.job_models.JobModalItem
import com.example.a09_noob_coders_android.data.models.news_modals.Article
import com.google.gson.Gson

class Repo() {
    suspend fun getNews(
        context: Context,
        _NewsList: SnapshotStateList<Article>?
    ): SnapshotStateList<Article>? {
        val gson = Gson()
        val url =
            "https://newsapi.org/v2/top-headlines?country=in&category=technology&apiKey=88d84abc92da4626a4b733c1e6762fa5"
        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
//                Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show()
                Toast.makeText(context, "Fetched data successfully", Toast.LENGTH_LONG).show()
                val articlesLi = response.getJSONArray("articles")
                for (i in 0 until articlesLi.length()) {
                    _NewsList?.add(
                        gson.fromJson(
                            articlesLi.getJSONObject(i).toString(),
                            Article::class.java
                        )
                    )
                }
            }, {
                Toast.makeText(context, "Unable to fetch data!!!", Toast.LENGTH_LONG).show()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }

        queue.add(jsonObjectRequest)

        return _NewsList
    }

    suspend fun getJobs(
        context: Context,
        _JobsList: MutableLiveData<SnapshotStateList<JobModalItem>>,
        query: MutableState<String>
    ): MutableLiveData<SnapshotStateList<JobModalItem>> {
        val gson = Gson()
        val url =
            "https://job-spoc-api.herokuapp.com/simplyhired/${query.value}"
        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = object : JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
//                Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show()
                Log.d("Success:", response.toString())
                Toast.makeText(context, "Fetched data successfully", Toast.LENGTH_LONG).show()
                _JobsList.value?.clear()
                for (i in 0 until response.length()) {
                    _JobsList.value?.add(
                        gson.fromJson(
                            response.getJSONObject(i).toString(),
                            JobModalItem::class.java
                        )
                    )
                }
            }, {
                Toast.makeText(context, "Unable to fetch data!!!", Toast.LENGTH_LONG).show()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }

        queue.add(jsonObjectRequest)

        return _JobsList
    }
}