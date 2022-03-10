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
        val counterResponseList = counterRemoteDataSource.getCounterResponseList()
        counterLocalDataSource.insertCounterResponseList(counterResponseList)
        return counterLocalDataSource.getCounterList()
    }

    override suspend fun saveCounter(title: String): List<Counter> {
        val counterResponseList = counterRemoteDataSource.saveCounter(title)
        counterLocalDataSource.insertCounterResponseList(counterResponseList)
        return counterLocalDataSource.getCounterList()
    }

    override suspend fun deleteCounter(counter: Counter): List<Counter> {
        val counterResponseList = counterRemoteDataSource.deleteCounter(counter)
        counterLocalDataSource.deleteAllAndInsertCounters(counterResponseList)
        return counterLocalDataSource.getCounterList()
    }
}
