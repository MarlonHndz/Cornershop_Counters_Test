package com.cornershop.remotedatasource.datasources

import com.cornershop.data.datasources.remotedatasource.CounterRemoteDataSource
import com.cornershop.data.datasources.remotedatasource.model.CounterResponse
import com.cornershop.remotedatasource.api.services.CounterService

class CounterRemoteDataSourceImpl(
    private val counterService: CounterService
) : CounterRemoteDataSource {

    override suspend fun getCounterResponseList(): List<CounterResponse> =
        counterService.getCounterResponseList()

    override suspend fun saveCounter(title: String) {
        counterService.saveCounter(title)
    }
}
