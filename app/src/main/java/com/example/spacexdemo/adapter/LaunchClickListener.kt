package com.example.spacexdemo.adapter

import android.view.View

interface LaunchClickListener {
    fun launchTapped(view: View,launchId: String)
}