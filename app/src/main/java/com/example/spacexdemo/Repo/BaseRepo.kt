package com.example.spacexdemo.Repo

import com.example.spacexdemo.Service.BaseHTTPService


class BaseRepo constructor(private val retrofitService: BaseHTTPService) {

    suspend fun getLaunches() = retrofitService.getLaunches()

}
