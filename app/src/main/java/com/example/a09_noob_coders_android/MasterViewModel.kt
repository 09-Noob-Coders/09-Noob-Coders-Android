package com.example.a09_noob_coders_android

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a09_noob_coders_android.data.models.AllEventModel
import com.example.a09_noob_coders_android.data.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MasterViewModel(
    job: Job
) : ViewModel() {

    private val myJob = job

    private val _allEventsList =
        MutableLiveData(mutableStateListOf<AllEventModel>())
    val allEventsList: SnapshotStateList<AllEventModel>? = _allEventsList.value
    fun getAllData(context: Context, userMainRepository: MainRepository, job: Job) {
        viewModelScope.launch(job + Dispatchers.Main) {
            viewModelScope.launch(Dispatchers.IO) {
                userMainRepository.getAllEvents(context, _allEventsList)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        myJob.cancel()
    }

}