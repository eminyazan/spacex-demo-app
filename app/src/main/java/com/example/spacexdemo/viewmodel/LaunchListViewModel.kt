package com.example.spacexdemo.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.spacexdemo.db.LaunchDatabase
import com.example.spacexdemo.repo.BaseRepo
import com.example.spacexdemo.model.Launch
import com.example.spacexdemo.model.LocalLaunch
import com.example.spacexdemo.service.BaseHTTPService
import kotlinx.coroutines.*

class LaunchListViewModel constructor(application: Application) : BaseViewModel(application) {

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean>
        get() = _loading

    private val _launches = MutableLiveData<MutableList<Launch>>()
    val launches: LiveData<MutableList<Launch>>
        get() = _launches

    private val retrofitService = BaseHTTPService.getInstance()
    private val mainRepository = BaseRepo(retrofitService)
    private var job: Job? = null

    private val launchDatabase by lazy {
        LaunchDatabase.getDatabase(getApplication()).launchDAO()
    }

    fun insertLaunch(localLaunch: LocalLaunch): Boolean {

        viewModelScope.launch {
            launchDatabase.insertLaunch(localLaunch)

        }
        return true
    }

    fun getAllLaunches() {
        _loading.value = true

        job = viewModelScope.launch {
            val res = async { mainRepository.getLaunches() }
            if (res.await().isSuccessful) {
                _launches.postValue(res.await().body())
                _loading.postValue(false)
            } else {
                _loading.postValue(false)
                // TODO: error value will be true
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}
