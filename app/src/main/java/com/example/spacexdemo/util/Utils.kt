package com.example.spacexdemo.util

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*


fun ImageView.downloadImageFromUrl(url: String?, context: Context) {
    val options = RequestOptions().placeholder(createPlaceHolder(context))
    url?.let {
        println("Null degıl")
        Glide.with(context).setDefaultRequestOptions(options).load(Uri.parse(it)).into(this)
    }

}


fun createPlaceHolder(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

@BindingAdapter("download_image")
fun downloadImage(imageView: ImageView, url: String?) {
    url?.let {
        imageView.downloadImageFromUrl(it, imageView.context)
    }

}

@BindingAdapter("convert_date")
fun convertDate(textView: TextView, timestamp: Long) {

    //TODO: Fix

    val stamp = Timestamp(timestamp)
    val date = Date(stamp.time)

    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    val formattedDate = sdf.format(date)

    println("Date --> $formattedDate")
    textView.text = formattedDate
}