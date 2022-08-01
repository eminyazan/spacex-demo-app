package com.example.spacexdemo.adapter

import android.view.View
import com.example.spacexdemo.model.Launch

interface LaunchClickListener {
    fun launchTapped(view: View,launchId: String)
}