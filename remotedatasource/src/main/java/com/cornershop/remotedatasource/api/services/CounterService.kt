package com.cornershop.remotedatasource.api.services

import com.cornershop.data.datasources.remotedatasource.model.CounterResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST

interface CounterService {

    @GET("api/v1/counters")
    suspend fun getCounterResponseList(): List<CounterResponse>

    @POST("/api/v1/counter")
    @FormUrlEncoded
    suspend fun saveCounter(@Field("title") title: String): List<CounterResponse>

    @HTTP(method = "DELETE", path = "/api/v1/counter", hasBody = true)
    @FormUrlEncoded
    suspend fun deleteCounter(@Field("id") id: String): List<CounterResponse>
}
