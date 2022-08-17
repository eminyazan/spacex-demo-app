package com.example.spacexdemo.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.spacexdemo.model.Launch
import com.example.spacexdemo.repo.BaseRepo
import com.example.spacexdemo.service.BaseHTTPService
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import okhttp3.internal.notifyAll
import retrofit2.Response

class LaunchDetailViewModel(application: Application) : BaseViewModel(application) {

    private val _launch = MutableLiveData<Launch>()
    val launch: LiveData<Launch>
        get() = _launch

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean>
        get() = _loading

    private val retrofitService = BaseHTTPService.getInstance()
    private val mainRepository = BaseRepo(retrofitService)
    private var job: Job? = null
   

    fun getLaunch(id: String) {
        job = viewModelScope.launch {
            val res = async { mainRepository.getLaunch(id = id) }
            if (res.await().isSuccessful) {
                _launch.postValue(res.await().body())
                _loading.postValue(false)
            } else {
                _loading.postValue(false)
                // TODO: error value will be true
            }
        }
    }

    override fun onCleared() {
        println("On cleared worked")
        super.onCleared()
        job?.cancel()
    }
}