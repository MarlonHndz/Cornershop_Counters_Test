package com.cornershop.data.repositories

import com.cornershop.data.datasources.localdatasource.CounterLocalDataSource
import com.cornershop.data.datasources.remotedatasource.CounterRemoteDataSource
import com.cornershop.domain.models.Counter
import com.cornershop.domain.repositories.CounterRepository

class CounterRepositoryImpl(
    private val counterRemoteDataSource: CounterRemoteDataSource,
    private val counterLocalDataSource: CounterLocalDataSource
) : CounterRepository {

    override suspend fun getCounterList(): List<Counter> {
        val counterResponse = counterRemoteDataSource.getCounterResponse()
        counterLocalDataSource.insertCounterResponse(counterResponse)
        return counterLocalDataSource.getCounterList()
    }
}
