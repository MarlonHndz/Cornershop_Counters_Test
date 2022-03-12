package com.cornershop.data.repositories

import androidx.lifecycle.MutableLiveData
import com.cornershop.data.datasources.localdatasource.CounterLocalDataSource
import com.cornershop.data.datasources.remotedatasource.CounterRemoteDataSource
import com.cornershop.domain.models.Counter
import com.cornershop.domain.repositories.CounterRepository
import com.cornershop.domain.repositories.serviceHandler.Result
import com.cornershop.domain.repositories.serviceHandler.ServiceCalled

class CounterRepositoryImpl(
    private val counterRemoteDataSource: CounterRemoteDataSource,
    private val counterLocalDataSource: CounterLocalDataSource
) : BaseRepository(), CounterRepository {

    override fun getResultStatusLiveData(): MutableLiveData<Result<Any>> {
        return getResultStatus()
    }

    override suspend fun getCounterList(): List<Counter> {
        makeApiCall(
            call = { counterRemoteDataSource.getCounterResponseList() },
            saveCallResultLocally = { counterLocalDataSource.insertCounterResponseList(it) },
            serviceCalled = ServiceCalled.COUNTER_GET,
            silentLoading = false
        )
        return counterLocalDataSource.getCounterList()
    }

    override suspend fun saveCounter(title: String): List<Counter> {
        makeApiCall(
            call = { counterRemoteDataSource.saveCounter(title) },
            saveCallResultLocally = { counterLocalDataSource.insertCounterResponseList(it) },
            serviceCalled = ServiceCalled.COUNTER_SAVE,
            silentLoading = false
        )
        return counterLocalDataSource.getCounterList()
    }

    override suspend fun deleteCounter(counter: Counter): List<Counter> {
        makeApiCall(
            call = { counterRemoteDataSource.deleteCounter(counter) },
            saveCallResultLocally = { counterLocalDataSource.deleteAllAndInsertCounters(it) },
            serviceCalled = ServiceCalled.COUNTER_DELETE,
            silentLoading = false
        )
        return counterLocalDataSource.getCounterList()
    }

    override suspend fun incrementTime(counter: Counter): List<Counter> {
        makeApiCall(
            call = { counterRemoteDataSource.incrementTime(counter) },
            saveCallResultLocally = { counterLocalDataSource.insertCounterResponseList(it) },
            serviceCalled = ServiceCalled.COUNTER_INCREMENT_TIME,
            silentLoading = false
        )
        return counterLocalDataSource.getCounterList()
    }

    override suspend fun decrementTime(counter: Counter): List<Counter> {
        makeApiCall(
            call = { counterRemoteDataSource.decrementTime(counter) },
            saveCallResultLocally = { counterLocalDataSource.insertCounterResponseList(it) },
            serviceCalled = ServiceCalled.COUNTER_DECREMENT_TIME,
            silentLoading = false
        )
        return counterLocalDataSource.getCounterList()
    }
}
