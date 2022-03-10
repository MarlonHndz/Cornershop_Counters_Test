package com.cornershop.data.datasources.remotedatasource

import com.cornershop.data.datasources.remotedatasource.model.CounterResponse

interface CounterRemoteDataSource {
    suspend fun getCounterResponseList(): List<CounterResponse>
    suspend fun saveCounter(title: String)
}
