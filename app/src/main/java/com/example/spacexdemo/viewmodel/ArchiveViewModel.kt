package com.example.spacexdemo.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.spacexdemo.db.LaunchDatabase
import com.example.spacexdemo.model.Launch
import com.example.spacexdemo.model.LocalLaunch
import com.example.spacexdemo.repo.BaseRepo
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ArchiveViewModel(application: Application) :
    BaseViewModel(application) {
    private var job: Job? = null

    val launchesList = MutableLiveData<MutableList<LocalLaunch>>()

    private val launchDatabase by lazy {
        LaunchDatabase.getDatabase(getApplication()).launchDAO()
    }


    fun getAllLaunchesFromLocal() {
        loading.value = true
        error.value = false
        viewModelScope.launch {
            val launches = launchDatabase.getAllLocalLaunches()
            if (launches.isNotEmpty()) {
                println("Local launches not empty")
                launchesList.postValue(launches.toMutableList())
                loading.value = false
                error.value = false
            } else {
                println("Local launches empty")
                loading.value = false
                error.value = true
            }
        }
    }



    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}