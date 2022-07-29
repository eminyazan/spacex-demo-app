package com.example.spacexdemo.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spacexdemo.repo.BaseRepo
import com.example.spacexdemo.model.Launch
import kotlinx.coroutines.*

class LaunchListViewModel constructor(private val mainRepository: BaseRepo) : ViewModel() {

    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()
    val launchesList = MutableLiveData<List<Launch>>()

    private var job: Job? = null

    fun getAllLaunches() {
        loading.value = true
        error.value = false
        Log.d("Thread Outside", Thread.currentThread().name)

        viewModelScope.launch {
            Log.d("Thread Inside", Thread.currentThread().name)
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
