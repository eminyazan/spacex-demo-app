package com.example.spacexdemo.repo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.spacexdemo.viewmodel.LaunchDetailViewModel
import com.example.spacexdemo.viewmodel.LaunchListViewModel

@Suppress("UNCHECKED_CAST")
class BaseViewModelFactory constructor(private val repository: BaseRepo) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LaunchListViewModel::class.java)) {
            LaunchListViewModel(this.repository) as T
        } else if (modelClass.isAssignableFrom(LaunchDetailViewModel::class.java)) {
            LaunchDetailViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}