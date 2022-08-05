package com.example.spacexdemo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData


abstract class BaseViewModel (application: Application): AndroidViewModel(application) {

    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()

}