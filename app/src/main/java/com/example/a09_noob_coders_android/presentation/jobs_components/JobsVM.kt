package com.example.docode.test.jobs_module

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a09_noob_coders_android.data.models.job_models.JobModalItem
import com.example.a09_noob_coders_android.data.repository.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class JobsVM(
    job: Job
) : ViewModel() {

    private val myJob = job

    private val _JobsList = MutableLiveData(mutableStateListOf<JobModalItem>())
    val getJobs: SnapshotStateList<JobModalItem>? = _JobsList.value
    fun getJobs(context: Context, repo: Repo, job: Job, query: MutableState<String>) {
        viewModelScope.launch(job + Dispatchers.Main) {
            viewModelScope.launch(Dispatchers.IO) {
                repo.getJobs(context, _JobsList, query)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        myJob.cancel()
    }
}