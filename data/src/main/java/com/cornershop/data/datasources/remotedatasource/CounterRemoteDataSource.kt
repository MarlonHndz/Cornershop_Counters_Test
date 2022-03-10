package com.cornershop.data.datasources.remotedatasource

import com.cornershop.data.datasources.remotedatasource.model.CounterResponse
import com.cornershop.domain.models.Counter

interface CounterRemoteDataSource {
    suspend fun getCounterResponseList(): List<CounterResponse>
    suspend fun saveCounter(title: String): List<CounterResponse>
    suspend fun deleteCounter(counter: Counter): List<CounterResponse>
}
