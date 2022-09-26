package com.example.a09_noob_coders_android.presentation.news_components

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a09_noob_coders_android.data.models.news_modals.Article
import com.example.a09_noob_coders_android.data.repository.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewsVM(
    job: Job
) : ViewModel() {

    private val myJob = job

    private val _NewsList = MutableLiveData(mutableStateListOf<Article>())
    val _getNews: SnapshotStateList<Article>? = _NewsList.value
    fun getNewsFunc(context: Context, repo: Repo, job: Job) {
        viewModelScope.launch(job + Dispatchers.Main) {
            viewModelScope.launch(Dispatchers.IO) {
                repo.getNews(context, _getNews)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        myJob.cancel()
    }
}