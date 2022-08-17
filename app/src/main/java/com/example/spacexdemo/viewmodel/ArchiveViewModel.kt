package com.example.spacexdemo.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.spacexdemo.db.LaunchDatabase
import com.example.spacexdemo.model.LocalLaunch
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ArchiveViewModel(application: Application) :
    BaseViewModel(application) {
    private var job: Job? = null

    val localLaunchesList = MutableLiveData<MutableList<LocalLaunch>>()

    private val launchDatabase by lazy {
        LaunchDatabase.getDatabase(getApplication()).launchDAO()
    }


    fun getAllLaunchesFromLocal() {
//        loading.value = true
//        error.value = false
        viewModelScope.launch {
            val launches = launchDatabase.getAllLocalLaunches()
            if (launches.isNotEmpty()) {
                println("Local launches not empty")
                localLaunchesList.postValue(launches.toMutableList())
//                loading.value = false
//                error.value = false
            } else {
                localLaunchesList.postValue(launches.toMutableList())
                println("Local launches empty")
//                loading.value = false
//                error.value = false
            }
        }
    }

    fun deleteLocalLaunch(localLaunch: LocalLaunch): Boolean {
        println("Deleted local launch --> ")
//        loading.value = true
//        error.value = false
        viewModelScope.launch {
            launchDatabase.deleteFromLocal(localLaunch)
            getAllLaunchesFromLocal()
        }
        return true
    }


    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}