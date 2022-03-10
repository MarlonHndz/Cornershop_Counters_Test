package com.cornershop.localdatasource.datasource

import com.cornershop.data.datasources.localdatasource.CounterLocalDataSource
import com.cornershop.data.datasources.localdatasource.models.CounterLocal
import com.cornershop.data.datasources.remotedatasource.model.CounterResponse
import com.cornershop.data.mappers.CounterLocalToCounterMapper
import com.cornershop.data.mappers.CounterResponseToCounterLocalMapper
import com.cornershop.domain.models.Counter
import com.cornershop.localdatasource.AppDatabase

class CounterLocalDataSourceImpl(
    private val appDatabase: AppDatabase,
    private val counterResponseToCounterLocalMapper: CounterResponseToCounterLocalMapper,
    private val counterLocalToCounterMapper: CounterLocalToCounterMapper
) : CounterLocalDataSource {

    override suspend fun insertCounterResponseList(counterResponseList: List<CounterResponse>) {
        val counterLocalList: List<CounterLocal> =
            counterResponseToCounterLocalMapper(counterResponseList)
        appDatabase.CounterDao().insertAll(counterLocalList)
    }

    override suspend fun getCounterList(): List<Counter> {
        return counterLocalToCounterMapper(appDatabase.CounterDao().getAll())
    }

    override suspend fun deleteAllAndInsertCounters(counterResponseList: List<CounterResponse>) {
        val counterLocalList: List<CounterLocal> =
            counterResponseToCounterLocalMapper(counterResponseList)
        appDatabase.CounterDao().deleteAllAndInsert(counterLocalList)
    }
}
