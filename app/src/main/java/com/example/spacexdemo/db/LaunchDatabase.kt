package com.example.spacexdemo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.spacexdemo.constans.DATABASE_NAME
import com.example.spacexdemo.model.*
import com.example.spacexdemo.service.LaunchDAO

@Database(
    entities = [LocalLaunch::class],
    version = 1,
    exportSchema = true
)

abstract class LaunchDatabase : RoomDatabase() {

    abstract fun launchDAO(): LaunchDAO

    companion object {

        @Volatile
        private var INSTANCE: LaunchDatabase? = null

        fun getDatabase(context: Context): LaunchDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildDatabase(context)
                }
            }
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): LaunchDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                LaunchDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}