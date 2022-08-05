package com.example.spacexdemo.model


import com.google.gson.annotations.SerializedName


data class Links(

    @SerializedName("patch")
    var patch: Patch,

    @SerializedName("reddit")
    var reddit: Reddit,

    
    @SerializedName("article")
    val article: String,
    
    @SerializedName("flickr")
    val flickr: Flickr,
    
    @SerializedName("presskit")
    val presskit: Any?,
    
    @SerializedName("webcast")
    val webcast: String,
    
    @SerializedName("wikipedia")
    val wikipedia: String,
    
    @SerializedName("youtube_id")
    val youtubeId: String
)