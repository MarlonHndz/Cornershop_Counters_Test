package com.cornershop.domain.useCases

import com.cornershop.domain.models.Counter
import com.cornershop.domain.repositories.CounterRepository

class CounterUseCase(
    private val counterRepository: CounterRepository
) : BaseUseCase() {

    suspend fun getCounterList(): List<Counter> {
        return counterRepository.getCounterList()
    }

    suspend fun saveCounter(title: String): List<Counter> {
        return counterRepository.saveCounter(title)
    }

    suspend fun deleteCounter(counter: Counter): List<Counter> {
        return counterRepository.deleteCounter(counter)
    }

    override fun getResultStatusLiveData() = counterRepository.getResultStatusLiveData()
}
