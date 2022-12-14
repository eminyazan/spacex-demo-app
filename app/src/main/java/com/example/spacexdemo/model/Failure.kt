package com.example.spacexdemo.model


import com.google.gson.annotations.SerializedName

data class Failure(
    @SerializedName("altitude")
    val altitude: Any?,
    @SerializedName("reason")
    val reason: String,
    @SerializedName("time")
    val time: Int
)