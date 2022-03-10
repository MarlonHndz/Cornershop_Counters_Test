package com.cornershop.domain.useCases

import com.cornershop.domain.models.Counter
import com.cornershop.domain.repositories.CounterRepository

class CounterUseCase(
    private val counterRepository: CounterRepository
) {
    suspend fun getCounterList(): List<Counter> {
        return counterRepository.getCounterList()
    }

    suspend fun saveCounter(title: String) {
        counterRepository.saveCounter(title)
    }
}
