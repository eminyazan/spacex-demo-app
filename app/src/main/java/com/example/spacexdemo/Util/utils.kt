package com.example.spacexdemo.util

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


fun ImageView.downloadImageFromUrl(url: String, context: Context) {
    val options = RequestOptions().placeholder(createPlaceHolder(context))
    Glide.with(context).setDefaultRequestOptions(options).load(Uri.parse(url)).into(this)
}

fun createPlaceHolder(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

@BindingAdapter("download_image")
fun downloadImage(imageView: ImageView, url: String) {
    imageView.downloadImageFromUrl(url, imageView.context)
}