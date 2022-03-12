package com.cornershop.data.datasources.remotedatasource

import com.cornershop.data.datasources.remotedatasource.model.CounterResponse
import com.cornershop.domain.models.Counter
import retrofit2.Response

interface CounterRemoteDataSource {
    suspend fun getCounterResponseList(): Response<List<CounterResponse>>
    suspend fun saveCounter(title: String): Response<List<CounterResponse>>
    suspend fun deleteCounter(counter: Counter): Response<List<CounterResponse>>
    suspend fun incrementTime(counter: Counter): Response<List<CounterResponse>>
    suspend fun decrementTime(counter: Counter): Response<List<CounterResponse>>
}
