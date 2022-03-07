package com.cornershop.localdatasource.datasource

import com.cornershop.data.datasources.localdatasource.CounterLocalDataSource
import com.cornershop.data.datasources.remotedatasource.model.CounterResponse
import com.cornershop.domain.models.Counter
import com.cornershop.localdatasource.AppDatabase

class CounterLocalDataSourceImpl(
    private val appDatabase: AppDatabase
) : CounterLocalDataSource {

    override suspend fun insertCounterResponse(counterResponse: CounterResponse) {
        TODO("Not yet implemented")
    }

    override suspend fun getCounterList(): List<Counter> {
        TODO("Not yet implemented")
    }
}
