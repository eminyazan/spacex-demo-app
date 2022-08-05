package com.example.spacexdemo.model


import com.google.gson.annotations.SerializedName


data class Reddit(

    @SerializedName("campaign")
    var campaign: String?,

    @SerializedName("launch")
    var launch: String?,



    @SerializedName("media")
    val media: Any?,

    @SerializedName("recovery")
    val recovery: Any?
)