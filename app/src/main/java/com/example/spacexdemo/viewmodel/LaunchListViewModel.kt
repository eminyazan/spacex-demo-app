package com.example.spacexdemo.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spacexdemo.model.Launch

class LaunchListViewModel(application: Application) : BaseViewModel(application) {
    val launches = MutableLiveData<List<Launch>>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()


    private fun showList(cookList: List<Launch>) {
        launches.value = cookList
        loading.value = false
        error.value = false
    }
}