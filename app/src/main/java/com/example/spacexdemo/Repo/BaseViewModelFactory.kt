package com.example.spacexdemo.Repo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.spacexdemo.viewmodel.LaunchListViewModel

class BaseViewModelFactory constructor(private val repository: BaseRepo): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LaunchListViewModel::class.java)) {
            LaunchListViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}