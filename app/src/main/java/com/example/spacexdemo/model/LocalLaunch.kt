package com.example.spacexdemo.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@kotlinx.serialization.Serializable
@Entity
data class LocalLaunch(
    @PrimaryKey
    var id: String,
    var name: String,
    var detail: String?=null,
    var largeImage: String,
    var smallImage: String,
    var date: Int,
):Serializable