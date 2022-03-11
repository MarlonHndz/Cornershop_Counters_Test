package com.cornershop.domain.repositories

import androidx.lifecycle.MutableLiveData
import com.cornershop.domain.models.Counter
import com.cornershop.domain.repositories.serviceHandler.Result

interface CounterRepository {
    fun getResultStatusLiveData(): MutableLiveData<Result<Any>>
    suspend fun getCounterList(): List<Counter>
    suspend fun saveCounter(title: String): List<Counter>
    suspend fun deleteCounter(counter: Counter): List<Counter>
}
