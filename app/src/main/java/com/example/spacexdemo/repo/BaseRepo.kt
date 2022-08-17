package com.example.spacexdemo.repo

import com.example.spacexdemo.model.Launch
import com.example.spacexdemo.service.BaseHTTPService
import retrofit2.Response


class BaseRepo constructor(
    private val retrofitService: BaseHTTPService,
) {

    suspend fun getLaunches() = retrofitService.getLaunches()
    suspend fun getLaunch(id: String) = retrofitService.getLaunch(id)

}
