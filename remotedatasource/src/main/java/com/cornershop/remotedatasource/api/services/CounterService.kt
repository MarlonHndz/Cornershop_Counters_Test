package com.cornershop.remotedatasource.api.services

import com.cornershop.data.datasources.remotedatasource.model.CounterResponse
import retrofit2.http.GET

interface CounterService {

    @GET("api/v1/counters")
    suspend fun getCounterResponse(): CounterResponse
}
