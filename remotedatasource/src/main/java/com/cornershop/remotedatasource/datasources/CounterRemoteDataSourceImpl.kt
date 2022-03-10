package com.cornershop.remotedatasource.datasources

import com.cornershop.data.datasources.remotedatasource.CounterRemoteDataSource
import com.cornershop.data.datasources.remotedatasource.model.CounterResponse
import com.cornershop.domain.models.Counter
import com.cornershop.remotedatasource.api.services.CounterService

class CounterRemoteDataSourceImpl(
    private val counterService: CounterService
) : CounterRemoteDataSource {

    override suspend fun getCounterResponseList(): List<CounterResponse> =
        counterService.getCounterResponseList()

    override suspend fun saveCounter(title: String): List<CounterResponse> {
        return counterService.saveCounter(title)
    }

    override suspend fun deleteCounter(counter: Counter): List<CounterResponse> {
        return counterService.deleteCounter(counter.id)
    }
}
