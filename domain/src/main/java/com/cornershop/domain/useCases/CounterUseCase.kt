package com.cornershop.domain.useCases

import com.cornershop.domain.models.Counter
import com.cornershop.domain.repositories.CounterRepository

class CounterUseCase(
    private val counterRepository: CounterRepository
) : BaseUseCase() {

    override fun getResultStatusLiveData() = counterRepository.getResultStatusLiveData()

    suspend fun getCounterList(): List<Counter> {
        return counterRepository.getCounterList()
    }

    suspend fun saveCounter(title: String): List<Counter> {
        return counterRepository.saveCounter(title)
    }

    suspend fun deleteCounter(counter: Counter): List<Counter> {
        return counterRepository.deleteCounter(counter)
    }

    suspend fun incrementTime(counter: Counter): List<Counter> {
        return counterRepository.incrementTime(counter)
    }

    suspend fun decrementTime(counter: Counter): List<Counter> {
        return counterRepository.decrementTime(counter)
    }
}
