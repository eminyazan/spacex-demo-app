package com.example.spacexdemo.adapter

import android.view.View
import com.example.spacexdemo.model.Launch
import com.example.spacexdemo.model.LocalLaunch


interface LaunchClickListener {
    fun launchTapped(view: View, launchId: String)
    fun launchLongTapped(view: View,launch: Launch):Boolean{
        return true
    }
    fun launchLongTapped(view: View,localLaunch: LocalLaunch):Boolean{
        return true
    }
}