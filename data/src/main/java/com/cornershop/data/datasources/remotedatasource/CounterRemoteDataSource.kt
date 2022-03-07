package com.cornershop.data.datasources.remotedatasource

import com.cornershop.data.datasources.remotedatasource.model.CounterResponse

interface CounterRemoteDataSource {
    suspend fun getCounterResponse(): CounterResponse
}
