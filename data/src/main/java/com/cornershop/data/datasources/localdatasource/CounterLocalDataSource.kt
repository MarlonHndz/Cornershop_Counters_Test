package com.cornershop.data.datasources.localdatasource

import com.cornershop.data.datasources.remotedatasource.model.CounterResponse
import com.cornershop.domain.models.Counter

interface CounterLocalDataSource {
    suspend fun insertCounterResponse(counterResponse: CounterResponse)
    suspend fun getCounterList(): List<Counter>
}
