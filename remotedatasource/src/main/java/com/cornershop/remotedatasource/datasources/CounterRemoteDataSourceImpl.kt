package com.cornershop.remotedatasource.datasources

import com.cornershop.data.datasources.remotedatasource.CounterRemoteDataSource
import com.cornershop.data.datasources.remotedatasource.model.CounterResponse
import com.cornershop.domain.models.Counter
import com.cornershop.remotedatasource.api.services.CounterService
import retrofit2.Response

class CounterRemoteDataSourceImpl(
    private val counterService: CounterService
) : CounterRemoteDataSource {

    override suspend fun getCounterResponseList(): Response<List<CounterResponse>> =
        counterService.getCounterResponseList()

    override suspend fun saveCounter(title: String): Response<List<CounterResponse>> {
        return counterService.saveCounter(title)
    }

    override suspend fun deleteCounter(counter: Counter): Response<List<CounterResponse>> {
        return counterService.deleteCounter(counter.id)
    }

    override suspend fun incrementTime(counter: Counter): Response<List<CounterResponse>> {
        return counterService.incrementTime(counter.id)
    }

    override suspend fun decrementTime(counter: Counter): Response<List<CounterResponse>> {
        return counterService.decrementTime(counter.id)
    }
}
