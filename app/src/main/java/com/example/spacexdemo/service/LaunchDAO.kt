package com.example.spacexdemo.service

import androidx.room.*
import com.example.spacexdemo.model.Launch
import com.example.spacexdemo.model.LocalLaunch

@Dao
interface LaunchDAO {
    @Query("SELECT * FROM locallaunch")
    suspend fun getAllLocalLaunches(): List<LocalLaunch>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLaunch(localLaunch: LocalLaunch)

    @Delete
    suspend fun deleteFromLocal(localLaunch: LocalLaunch)
}