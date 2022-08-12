package com.example.spacexdemo.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.spacexdemo.db.LaunchDatabase
import com.example.spacexdemo.repo.BaseRepo
import com.example.spacexdemo.model.Launch
import com.example.spacexdemo.model.LocalLaunch
import com.example.spacexdemo.service.BaseHTTPService
import kotlinx.coroutines.*

class LaunchListViewModel constructor(application: Application) : BaseViewModel(application) {

    val launchesList = MutableLiveData<MutableList<Launch>>()
    private val retrofitService = BaseHTTPService.getInstance()
    private val mainRepository = BaseRepo(retrofitService)
    private var job: Job? = null

    private val launchDatabase by lazy {
        LaunchDatabase.getDatabase(getApplication()).launchDAO()
    }
    fun insertLaunch(localLaunch: LocalLaunch): Boolean {
        loading.value = true
        error.value = false
        viewModelScope.launch {
            launchDatabase.insertLaunch(localLaunch)
            loading.value = false
            error.value = false
        }
        return true
    }

    fun getAllLaunches() {
        println("Get all launch view model ---> ${launchesList.value?.size}")
        loading.value = true
        error.value = false

        viewModelScope.launch {
            val res = mainRepository.getLaunches()
            if (res.isSuccessful) {
                launchesList.postValue(res.body())
                loading.value = false
                error.value = false
            } else {
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
