package com.example.spacexdemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()

}