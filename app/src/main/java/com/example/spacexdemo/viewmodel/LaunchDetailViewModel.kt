package com.example.spacexdemo.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.spacexdemo.model.Launch
import com.example.spacexdemo.repo.BaseRepo
import com.example.spacexdemo.service.BaseHTTPService
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.internal.notifyAll

class LaunchDetailViewModel(application: Application) : BaseViewModel(application) {

    val launch = MutableLiveData<Launch?>()
    private val retrofitService = BaseHTTPService.getInstance()
    private val mainRepository = BaseRepo(retrofitService)
    private var job: Job? = null

    fun getLaunch(id: String) {
        loading.value = true
        error.value = false

        viewModelScope.launch {
            val res = mainRepository.getLaunch(id = id)
            if (res.isSuccessful) {
                println("View model get launch --> ${res.body()?.links?.patch?.large}")
                launch.postValue(res.body())
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