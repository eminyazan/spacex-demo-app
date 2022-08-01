package com.example.spacexdemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spacexdemo.model.Launch
import com.example.spacexdemo.repo.BaseRepo
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LaunchDetailViewModel(private val repo: BaseRepo) : ViewModel() {

    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()
    val launch = MutableLiveData<Launch?>()

    private var job: Job? = null

    fun getLaunch(id:String) {
        loading.value = true
        error.value = false

        viewModelScope.launch {
            val res = repo.getLaunch(id = id)
            if (res.isSuccessful) {
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