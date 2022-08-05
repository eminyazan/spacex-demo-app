package com.example.spacexdemo.model


import com.google.gson.annotations.SerializedName

data class Patch(

    @SerializedName("large")
    var large: String,

    @SerializedName("small")
    var small: String
)