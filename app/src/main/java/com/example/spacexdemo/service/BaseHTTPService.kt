package com.example.spacexdemo.service

import com.example.spacexdemo.constans.BASE_URL
import com.example.spacexdemo.model.Launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface BaseHTTPService {

    @GET("v5/launches")
    suspend fun getLaunches(): Response<List<Launch>>

    companion object {

        var retrofitService: BaseHTTPService? = null

        fun getInstance() : BaseHTTPService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(BaseHTTPService::class.java)
            }
            return retrofitService!!
        }

    }
}